The app language is 100% Kotlin
For the architecture we decided to use the MVVM pattern.
For dependency injection we used Koin.
For the API calls we used Retrofit with Kotlin Coroutines 


How to use the app?

When you start the app for the first time, you should create a new user by filling in the given fields and optionally selecting a profile photo.
Once the registration requirements are met, you will be taken directly to the home feed screen. From there you can navigate to the group screen and create a new group by clicking on the floating action button. To create a group, you have to fulfill the given conditions. Sometimes the selected user may not be selected correctly, please try again. After successfully creating a group, you will see your group and if there are invitations from other users their invitations. You can then navigate to the group details, and see your group and the members, possibly you can invite other users. You can change or delete the name, the photo and also the name. If you don't feel like it anymore, just delete it. After that you can navigate to the new story and post a story and optionally tag users of the group.
After creating a post, you will be navigated to the homefeed from there you can see all the posts from the groups, by clicking on the search icon on the toolbar also select the group from which you want to see the posts. You can delete your post, write comments and also delete your own comments. 
The user/delete call is unfortunately not working, it has been reported and there are apparently issues on the REST API that have not been fixed yet.



Unfortunately we made a small mistake due to the merge.
Please replace the return true in the settings fragment on line 87, with the following code -> return super.onOptionsItemSelected(item)
And on the GroupCreateFragment.kt at line 106, under the if please add this code "adapter1.currentList"
There may be some bugs, please be considerate.
Thank you.
