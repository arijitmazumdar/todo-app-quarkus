# Develop your to-do app

Hi! Let's build this app together.

## Setup
1. Register yourself openshift.io. (Please don't forget the userid while registering)
2. Login with your newly created credentials
3. Create a workspace of type Quarkus tools
4. Once login clone code from https://github.com/arijitmazumdar/todo-app-quarkus.git

## Code along

* I guess, you have downloaded repository into your eclipse che environment. This is a working application with a PostGre database, but will not work here in Eclipse Che environment.  
* First we need to change the database from prostgre to H2. Open `src/main/resources/application.properties`. Change the following proporeties as shown below. With this we can run the aaplication with H2 in-memory database without any code change 

```
quarkus.datasource.url=jdbc:h2:mem:tododb
quarkus.datasource.driver=org.h2.Driver
quarkus.datasource.username=username-default
quarkus.datasource.password=

```
Once these changes done, one can run the application using the following command.
``` Shell
# terminal one 
mvn compile quarkus:dev

```

* Add a service with an endpoint `/others/message`. Open `todo-app-quarkus/src/main/java/org/todo/`. Add the following code 

``` java
	@GET
    @Path("/others/message")
    public String message() {
        String[] quotesArray = {
            "Brevity is beautiful",
            "I don’t want to earn my living; I want to live.",
            "Live for yourself.",
            "Life is short. Live passionately.",
            "Life shrinks or expands in proportion to one’s courage.",
            "Life is a one time offer, use it well.",
            "The trouble is you think you have time.",
            "Whatever you are, be a good one.",
            "Everything happens for a reason.",
            "Be the change you wish to see in the world.",
            "Youth is counted sweetest by those who are no longer young."           
        }; 

        int index = (int)(Math.random() * 10);
        return quotesArray[index];
    }

```
Don't forget to add the following import statement

``` java
import java.lang.Math; 
```

You can test this using following commands (keep the application running)
``` Shell
#terminal two
curl localhost:8080/others/message
I don’t want to earn my living; I want to live.
```

* Now let's integrate with our application. Open `src/main/resources/META-INF/resources/index.html`. Add the following line just after `</ul>`


``` javascript
<div id="message"></div>
<script>
    $.ajax({
        url: '/todos/others/message',
        dataType: 'text',
        type: 'GET',
        async: true,
        statusCode: {
            404: function (response) {
                alert(404);
            },
            200: function (response) {                
                document.getElementById("message").innerHTML="<i>"+response+"</i>";
            }
        },
        error: function (jqXHR, status, errorThrown) {
            alert('error');
        }
    });
</script>
```
Just refresh the webpage to see the change.. (Hope the quarkus application is still running)
