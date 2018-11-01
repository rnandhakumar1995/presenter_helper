# presenter_helper
Helps to implement MVP easily

Add the following gradle to your project.
  compile 'com.naaluzhavu.helper:helper:0.0.5'
Sync the Gradle.
Once the gradle synced, the helper can be used anywhere in the project. The helper library contains two classes BasePresenter and BaseViewActivity.
To use this, extend BaseViewActivity to your activity class. Now you can use methods like showToast, getText, isFilled, showAlert, showProgress, hideProgress, formatMongoDate, formatDate.
To use BasePresenter, extend BasePresenter to you presenter class. The BasePresenter contains log()[to log], getRetrofit() [to get instance of retrofit], stringToPojo() [to convert string to POJO list], stringToPojoObject() [to convert string to POJO object], pojoToString() [to convert pojo to string], writeToFile() [to write string content to file], deleteFile() [to delete a file], readFromFile() [to read a file], makeServerCall() [send request to server and handles error response] and an abstract method onResponse(), which will be called from makeServerCall() to deliver response to desired presenter class.
