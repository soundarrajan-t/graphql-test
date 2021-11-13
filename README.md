### Project Configuration

    Java version 11 is required 

## GoPass

    Download and setup Gopass
    create a store with variables as mentioned in our Documentation

    Working With GoPass!!!
    
    First install GPG and gopass in your machine.
    
    You can have a store either in your local machine or with some external resources.
    
    Steps needed in our project:

    Install gpg:
    brew install gnupg2
    
    Generate a new key:
    gpg --full-generate-key

    To list the keys in the machine:
    gpg -k

    Install gopass:
    brew install gopass

    do a gopass initial setup with a key
    gopass setup
    
    create a store named variables:
    gopass init --store variables

    Insert all the variables for the project using this command
    $ gopass edit variables/graphql
    
    MYSQL_DATABASE: bookmyshow
    DB_HOST: book-my-show-db
    DB_PORT: 3306
    DB_USERNAME: root
    OMDB_API: 99c8f50c
    MYSQL_ROOT_PASSWORD: 123456
    OMDB_URL: https://www.omdbapi.com
    OMDB_TEST_URL:  /www.omdbapi.com/

    Paste all the above key value pairs

    After inserting all the variables, check for all the variables with
    gopass variables/graphql
    
    If you want to have a shared store then you can also try the above mentioned step to achieve that.
    
    For more Reference: https://github.com/gopasspw/gopass, https://woile.github.io/gopass-cheat-sheet/

### [To setup Gopass](https://www.gopass.pw/#install)

## Running Application

    Dependency 
        - Docker

### [To install docker](https://docs.docker.com/engine/install/)

### Run Command

    ./scripts/run.sh

    By running this command, you will be provided with options of what to run.
    we can select which task to run:
        0.all tests  
        1.unit tests 
        2.integration tests
        3.start application
        4.start application in deploy mode
        5.cancel


 			 			 			
