# Github Miner
GithubMiner is an open source tool that connects to a Github server and fetches basic information about Pull Requests and Code Review activities.

This app also contains a Kotlin library for using the Github REST API.

Visit us on [Github](https://github.com/dxworks/github-miner).

## Download
Please download our [latest release](https://github.com/dxworks/github-miner/releases) from Github and unzip the folder. 
The contents of the folder are as follows:
* `githubminer.jar` (executable jar file)
* `githubminer.sh` (executable file for linux / macOS)
* `githubminer.bat` (executable file for windows)
* `config/github-miner.properties` (configuration folder)
* `results` (a folder for GithubMiner to put result files in)

## Configure
To configure GithubMiner, please open the config/github-miner.properties and add the configuration fields.

#### github.base.path
the URL to the Github Enterprise servers, postfixed with /api/v3; an example would be
https://github.my-organisation.com/api/v3. If omitted, the default value is the Github Cloud API url: https://api.github.com

#### github.tokens
A comma separated list of [Personal Access Tokens](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token), used for authentication. If the server you are connected to has Rate Limits activated (like Github Cloud does), Github Miner will perform faster if multiple tokens (from different users) are provided.

#### github.repos
A comma separated list of repositories, in the for `owner/repo`, For example, for the Kafka and Spring Framework repository, the value would be: `apache/kafka,spring-projects/spring-framework`

## Run
Run GithubMiner using the `githubminer.bat` or `githubminer.sh` script, with no parameters. When GithubMiner finishes it will generate a JSON file, placed in the “results” sub-folder, for each of the repositories given as parameters. The name of the files are `ABC-prs.json`, whereby `ABC` is the repository name.
