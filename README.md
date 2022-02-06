## Quick Start Up

### Git
1. Clone the code from this repository
2. Checkout the _**develop**_ branch, use this branch to create your changes

Important! _**master**_ branch has the latest release content
### Create your Gradle Configuration
1. Got **Run** menu
2. Select **Edit Configurations...**:
   1. Name your Configuration
   2. Under **Run**, add ```clean build bootRun```
      - Tip #1: add ```-x test``` if you would like to skip all test, make sure you ran proper tests before pushing your changes
      - Tip #2: add ```--offline``` if you don't need to download libraries or are having issues synchronizing with your repo
      - ![Run/Debug Dialog Configurations](/assets/RunDebug.png)
   3. Set the **Environment variables**:
      1. Past the content below in the text box:
         - ```CONTACT_LIST_BASE_URL=https://cfa41fbb-28c9-4f12-9b93-afd566d196a8.mock.pstmn.io/api/v1;CONTACT_LIST_ENDPOINT=/contacts;API_KEY=113e22af7a6564e1d33266e3429ecc08-us14;DC_ID=us14```
         - ![Environment Variables Dialog](/assets/EnvVars.png)
3. Click on **OK** to save your changes
4. To Start your application click **Run** menu and select **Run '{config_name}'** or **Debug '{config_name}'**
### Environment Variables
```
CONTACT_LIST_BASE_URL      https://cfa41fbb-28c9-4f12-9b93-afd566d196a8.mock.pstmn.io/api/v1
CONTACT_LIST_ENDPOINT      /contacts
API_KEY                    113e22af7a6564e1d33266e3429ecc08-us14
DC_ID                      us14
```
### Project Structure
```bash
├─ src
│  ├── main
│      ├── java
│          ├── com.trio.rmacaroun.takehome.mailinglist
│              ├── client
│              ├── config
│              ├── controller
│              ├── decoder
│              ├── dto
│              ├── mapper
│              ├── service
│              └── MailingListApplication.java
│      └── resources
│          └── appliation.yml
│  └── test
│      └── java
│          └── com.trio.rmacaroun.takehome.mailinglist
│              ├── integration
│              ├── service
│              └── MailingListApplicationTests.java
├── build.gradle
└── README.md
```
## External Information
### Mailchimp
1. API Keys
   1. Access your account profile
   2. Click on **Extras** and select **API Keys**
      - If there isn't an existing key, please create one
2. Data Center
   1. After logging in your account, your Data Center is between ```https://``` and ```.admin.mailchimp.com/account/profile/```
      - Example: In https://us14.admin.mailchimp.com/account/profile/, Data Center is ```us14```
3. Postman collection used to support the development: ```assets/Mailing_List.postman_collection.json```

### Useful Links:
- https://mailchimp.com/resources/one-audience-organize-contacts-to-optimize-marketing/