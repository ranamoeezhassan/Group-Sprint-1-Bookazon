import json
import os
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
from datetime import datetime, timedelta, timezone

def generate_burndown_data():
    """Generate burndown data from issues."""
    # Read the issues
    with open('burndown/data/issues.json', 'r') as f:
        issues = json.load(f)
    
    if not issues:
        print("No issues found!")
        return None
    
    # Find sprint start and end dates
    all_dates = []
    for issue in issues:
        all_dates.append(datetime.fromisoformat(issue['createdAt'].replace('Z', '+00:00')))
        if issue['closedAt']:
            all_dates.append(datetime.fromisoformat(issue['closedAt'].replace('Z', '+00:00')))
    
    sprint_start = min(all_dates).replace(hour=0, minute=0, second=0, microsecond=0)
    sprint_end = max(all_dates).replace(hour=23, minute=59, second=59, microsecond=0)
    
    # If there are open issues, extend to current date
    if any(not issue['closedAt'] for issue in issues):
        sprint_end = datetime.now(timezone.utc).replace(hour=23, minute=59, second=59, microsecond=0)
    
    # Create daily snapshots
    current = sprint_start
    daily_data = []
    
    while current <= sprint_end:
        open_count = 0
        closed_count = 0
        created_today = 0
        closed_today = 0
        
        for issue in issues:
            created = datetime.fromisoformat(issue['createdAt'].replace('Z', '+00:00'))
            closed = datetime.fromisoformat(issue['closedAt'].replace('Z', '+00:00')) if issue['closedAt'] else None
            
            if created.date() == current.date():
                created_today += 1
            
            if closed and closed.date() == current.date():
                closed_today += 1
            
            if created <= current:
                if not closed or closed > current:
                    open_count += 1
                else:
                    closed_count += 1
        
        daily_data.append({
            'date': current.strftime('%Y-%m-%d'),
            'open': open_count,
            'closed': closed_count,
            'created_today': created_today,
            'closed_today': closed_today
        })
        
        current += timedelta(days=1)
    
    return {
        'daily_data': daily_data,
        'sprint_start': sprint_start.isoformat(),
        'sprint_end': sprint_end.isoformat(),
        'total_issues': len(issues)
    }

def create_burndown_chart(data):
    """Create the burndown chart visualization."""
    daily_data = data['daily_data']
    total_issues = data['total_issues']
    
    # Extract data for plotting
    dates = [datetime.strptime(day['date'], '%Y-%m-%d') for day in daily_data]
    actual_open = [day['open'] for day in daily_data]
    created_daily = [day['created_today'] for day in daily_data]
    closed_daily = [day['closed_today'] for day in daily_data]
    
    # Calculate ideal burndown
    days = len(daily_data)
    ideal_burndown = [max(0, total_issues - (total_issues / days * (i + 1))) for i in range(days)]
    
    # Create figure with multiple subplots
    fig = plt.figure(figsize=(16, 10))
    gs = fig.add_gridspec(3, 2, hspace=0.3, wspace=0.3)
    
    # 1. Main Burndown Chart
    ax1 = fig.add_subplot(gs[0, :])
    ax1.plot(dates, actual_open, 'o-', linewidth=3, markersize=10, 
             color='#e74c3c', label='Actual Open Issues', zorder=3)
    ax1.plot(dates, ideal_burndown, '--', linewidth=2.5, 
             color='#3498db', label='Ideal Burndown', alpha=0.7, zorder=2)
    ax1.fill_between(dates, actual_open, ideal_burndown, 
                      where=[a >= i for a, i in zip(actual_open, ideal_burndown)],
                      color='#e74c3c', alpha=0.1, label='Behind Schedule')
    ax1.fill_between(dates, actual_open, ideal_burndown, 
                      where=[a < i for a, i in zip(actual_open, ideal_burndown)],
                      color='#2ecc71', alpha=0.1, label='Ahead of Schedule')
    ax1.axhline(y=0, color='#2ecc71', linestyle='-', linewidth=2, alpha=0.3)
    
    repo_name = 'Sprint Burndown Chart'
    ax1.set_title(repo_name, fontsize=18, fontweight='bold', pad=20)
    ax1.set_xlabel('Date', fontsize=13, fontweight='bold')
    ax1.set_ylabel('Number of Open Issues', fontsize=13, fontweight='bold')
    ax1.grid(True, alpha=0.3, linestyle='--', linewidth=0.8)
    ax1.legend(loc='upper right', fontsize=11, framealpha=0.95)
    ax1.xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d'))
    ax1.xaxis.set_major_locator(mdates.DayLocator())
    plt.setp(ax1.xaxis.get_majorticklabels(), rotation=0, ha='center')
    
    for i, (date, value) in enumerate(zip(dates, actual_open)):
        ax1.annotate(f'{value}', xy=(date, value), xytext=(0, 12),
                    textcoords='offset points', ha='center',
                    fontsize=11, fontweight='bold', color='#e74c3c',
                    bbox=dict(boxstyle='round,pad=0.3', facecolor='white', 
                             edgecolor='#e74c3c', alpha=0.8))
    
    ax1.set_ylim(bottom=-2, top=max(max(actual_open), max(ideal_burndown)) + 5)
    
    # 2. Daily Activity Bar Chart
    ax2 = fig.add_subplot(gs[1, 0])
    x = range(len(dates))
    width = 0.35
    bars1 = ax2.bar([i - width/2 for i in x], created_daily, width, 
                    label='Created', color='#f39c12', alpha=0.8, edgecolor='black')
    bars2 = ax2.bar([i + width/2 for i in x], closed_daily, width, 
                    label='Closed', color='#2ecc71', alpha=0.8, edgecolor='black')
    
    for bars in [bars1, bars2]:
        for bar in bars:
            height = bar.get_height()
            if height > 0:
                ax2.text(bar.get_x() + bar.get_width()/2., height,
                        f'{int(height)}', ha='center', va='bottom', 
                        fontsize=10, fontweight='bold')
    
    ax2.set_title('Daily Issue Activity', fontsize=14, fontweight='bold')
    ax2.set_xlabel('Date', fontsize=11, fontweight='bold')
    ax2.set_ylabel('Number of Issues', fontsize=11, fontweight='bold')
    ax2.set_xticks(x)
    ax2.set_xticklabels([d.strftime('%m-%d') for d in dates])
    ax2.legend(loc='upper left', fontsize=10)
    ax2.grid(True, alpha=0.3, linestyle='--', axis='y')
    
    # 3. Cumulative Progress
    ax3 = fig.add_subplot(gs[1, 1])
    cumulative_closed = [total_issues - day['open'] for day in daily_data]
    ax3.plot(dates, cumulative_closed, 'o-', linewidth=2.5, markersize=8, 
             color='#2ecc71', label='Cumulative Closed', zorder=3)
    ax3.axhline(y=total_issues, color='#e74c3c', linestyle='--', 
               linewidth=2, alpha=0.5, label=f'Total ({total_issues})')
    ax3.fill_between(dates, 0, cumulative_closed, color='#2ecc71', alpha=0.2)
    
    ax3.set_title('Cumulative Issues Closed', fontsize=14, fontweight='bold')
    ax3.set_xlabel('Date', fontsize=11, fontweight='bold')
    ax3.set_ylabel('Issues Closed', fontsize=11, fontweight='bold')
    ax3.set_xticks(dates)
    ax3.set_xticklabels([d.strftime('%m-%d') for d in dates])
    ax3.legend(loc='upper left', fontsize=10)
    ax3.grid(True, alpha=0.3, linestyle='--')
    
    for i, (date, value) in enumerate(zip(dates, cumulative_closed)):
        ax3.annotate(f'{value}', xy=(date, value), xytext=(0, 8),
                    textcoords='offset points', ha='center',
                    fontsize=9, fontweight='bold', color='#2ecc71')
    
    # 4. Statistics Summary
    ax4 = fig.add_subplot(gs[2, 0])
    ax4.axis('off')
    
    closed_issues = total_issues - actual_open[-1]
    completion_rate = (closed_issues / total_issues * 100)
    days_active = len([d for d in daily_data if d['closed_today'] > 0])
    avg_velocity = closed_issues / days_active if days_active > 0 else 0
    peak_day = max(daily_data, key=lambda x: x['closed_today'])
    
    stats_text = f"""
SPRINT STATISTICS

Total Issues:           {total_issues}
Closed Issues:          {closed_issues}
Remaining Open:         {actual_open[-1]}
Completion Rate:        {completion_rate:.1f}%

PERFORMANCE METRICS

Average Velocity:       {avg_velocity:.1f} issues/day
Most Productive Day:    {peak_day['date']}
                        ({peak_day['closed_today']} closed)
Sprint Duration:        {len(daily_data)} days
Active Days:            {days_active} days
"""
    
    ax4.text(0.1, 0.9, stats_text, transform=ax4.transAxes,
             fontsize=11, verticalalignment='top', family='monospace',
             bbox=dict(boxstyle='round', facecolor='#ecf0f1', alpha=0.8, pad=1))
    
    # 5. Velocity Trend
    ax5 = fig.add_subplot(gs[2, 1])
    velocity = closed_daily
    ax5.bar(range(len(dates)), velocity, color='#9b59b6', alpha=0.7, edgecolor='black')
    if days_active > 0:
        ax5.axhline(y=avg_velocity, color='#e74c3c', linestyle='--', 
                   linewidth=2, label=f'Avg: {avg_velocity:.1f}')
    
    ax5.set_title('Daily Velocity (Issues Closed)', fontsize=14, fontweight='bold')
    ax5.set_xlabel('Date', fontsize=11, fontweight='bold')
    ax5.set_ylabel('Issues Closed', fontsize=11, fontweight='bold')
    ax5.set_xticks(range(len(dates)))
    ax5.set_xticklabels([d.strftime('%m-%d') for d in dates])
    ax5.legend(loc='upper right', fontsize=10)
    ax5.grid(True, alpha=0.3, linestyle='--', axis='y')
    
    for i, value in enumerate(velocity):
        if value > 0:
            ax5.text(i, value, f'{value}', ha='center', va='bottom', 
                    fontsize=10, fontweight='bold')
    
    fig.suptitle('Sprint Burndown Analysis Dashboard', 
                 fontsize=20, fontweight='bold', y=0.98)
    
    # Save the figure
    os.makedirs('burndown', exist_ok=True)
    plt.savefig('burndown/burndown_chart.png', dpi=300, bbox_inches='tight', facecolor='white')
    plt.close()
    
    print(f"✅ Burndown chart saved as 'burndown/burndown_chart.png'")
    print(f"   Total Issues: {total_issues}")
    print(f"   Closed: {closed_issues} ({completion_rate:.1f}%)")
    print(f"   Remaining: {actual_open[-1]}")

if __name__ == '__main__':
    print("Generating burndown data...")
    data = generate_burndown_data()
    
    if data:
        # Save data
        os.makedirs('burndown/data', exist_ok=True)
        with open('burndown/data/burndown_data.json', 'w') as f:
            json.dump(data, f, indent=2)
        print("✅ Burndown data saved to burndown/data/burndown_data.json")
        
        # Create chart
        print("Creating burndown chart...")
        create_burndown_chart(data)
        print("✅ Done!")
    else:
        print("❌ Failed to generate burndown data")
