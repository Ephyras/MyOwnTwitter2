MyOwnTwitter2 implements a message sharing application. Users login into the system to post messages, and then other users can see all the stream of messages.

Right now the data persistence for Users and Messages is implemented with Room (https://developer.android.com/topic/libraries/architecture/room.html) so there is no "message sharing" yet. However, this is still a good example of how implement data persistence with Room (and SQLite)


Known issues:

- The ListView on the MessagesActivity does not properly refresh/update after the user enters a new message
- Missing several error check-ins. Undefined behavior under invalid user input or after no records coming from SQLite
 

