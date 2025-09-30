import json
import os
import requests

def fetch_all_issues():
    """Fetch all issues from the GitHub repository."""
    token = os.environ.get('GITHUB_TOKEN')
    owner = os.environ.get('REPO_OWNER')
    repo = os.environ.get('REPO_NAME')
    
    if not all([token, owner, repo]):
        print("Error: Missing required environment variables")
        print(f"GITHUB_TOKEN: {'set' if token else 'missing'}")
        print(f"REPO_OWNER: {owner or 'missing'}")
        print(f"REPO_NAME: {repo or 'missing'}")
        exit(1)
    
    headers = {
        'Authorization': f'token {token}',
        'Accept': 'application/vnd.github.v3+json'
    }
    
    all_issues = []
    page = 1
    per_page = 100
    
    print(f"Fetching issues from {owner}/{repo}...")
    
    while True:
        url = f'https://api.github.com/repos/{owner}/{repo}/issues'
        params = {
            'state': 'all',
            'per_page': per_page,
            'page': page
        }
        
        response = requests.get(url, headers=headers, params=params)
        
        if response.status_code != 200:
            print(f"Error fetching issues: {response.status_code}")
            print(response.text)
            exit(1)
        
        issues = response.json()
        
        if not issues:
            break
        
        # Filter out pull requests (they appear in the issues API)
        issues = [issue for issue in issues if 'pull_request' not in issue]
        
        all_issues.extend(issues)
        print(f"Fetched page {page}: {len(issues)} issues")
        
        page += 1
        
        # Break if we got fewer items than requested (last page)
        if len(issues) < per_page:
            break
    
    print(f"Total issues fetched: {len(all_issues)}")
    
    # Transform to our format
    transformed_issues = []
    for issue in all_issues:
        transformed_issues.append({
            'number': issue['number'],
            'title': issue['title'],
            'createdAt': issue['created_at'],
            'closedAt': issue['closed_at'],
            'labels': [{'name': label['name']} for label in issue['labels']]
        })
    
    # Save to JSON file
    os.makedirs('burndown/data', exist_ok=True)
    with open('burndown/data/issues.json', 'w') as f:
        json.dump(transformed_issues, f, indent=2)
    
    print(f"✅ Issues saved to burndown/data/issues.json")

if __name__ == '__main__':
    fetch_all_issues()
