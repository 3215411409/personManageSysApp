### Employee Management System: README

---

## **Project Overview**

This is an Employee Management System developed using Android Studio. The application aims to provide a convenient way for administrators to manage employee details and for employees to access and update their own information. It also incorporates features such as push notifications and interaction with a RESTful API.

---

## **Features**

- **Admin Side:**
  -Add, check, edit, and delete employee details.
  -Approve or decline employee holiday requests.
  -Automatic salary increment by 5% when an employee completes one year of service.
- **Employee Side:**
  - View and edit personal details.
  - Book and manage annual leave (with an allowance of 30 days per year).
- **Common Features:**
  - User authentication (login).
  - Notification management (turn on/off based on preferences).
 
---

## **Installation**
1. **Clone the repository to your local machine using the following command:**
   ```bash
   git clone https://github.com/3215411409/personManageSysApp
2.**Open the project in Android Studio.**
3.**Ensure that you have the necessary Android SDK and dependencies installed. You may need to update the Gradle version if there are any compatibility issues.**
4.**Sync the project with Gradle to download all the required dependencies.**

   ---

## **API Integration**
The application communicates with a RESTful API to perform various operations. To set up the API connection, make sure to configure the base URL and authentication details in the appropriate places in the code. A worker thread is used to handle the API calls to ensure smooth and responsive application performance.

   ---

## **Design Patterns Used**

- **Model-View-Controller (MVC) Pattern: The application is structured following the MVC pattern. The model represents the data (employee information, etc.), the view is responsible for presenting the UI, and the controller handles the user interactions and updates the model and view accordingly. This separation of concerns makes the codebase more maintainable and easier to understand.**
- **Observer Pattern: Used for handling push notifications. The application registers as an observer to listen for changes in the data related to employee requests or updates, and when such changes occur, the appropriate notifications are pushed to the users.**

   ---

## **Usability Evaluation**

A usability evaluation was conducted with at least two users. The evaluation process included the following steps:

- **User Testing Plan: A detailed plan was designed to test the various features of the application. Users were asked to perform tasks such as logging in, adding/editing employee details (for admins), booking leave (for employees), and managing notifications.**
- **Demographics of Participants: The participants had different levels of experience with mobile applications and were from diverse backgrounds to ensure a comprehensive evaluation.**
- **Consent Form: Participants signed a consent form before the testing to ensure their willingness to participate and to allow the collection of data related to their usage of the application.**
- **Outcome of the Study: Based on the user feedback, several improvements were made to the UI design and the functionality. For example, the layout of some screens was adjusted to make it more intuitive, and the process of approving/declining holiday requests was streamlined.**

   ---

## **Screenshots and UI Design**

- **The UI design was carefully crafted considering the principles of Human-Computer Interaction (HCI). The application has a clean and intuitive interface for both the admin and employee sides. Screenshots of the various screens can be found in the pdf.**

## ** Troubleshooting**

 - **If you encounter any issues during the installation or running of the application, please check the following:**
   - Make sure you have the correct Android SDK version installed.
   - Check if there are any conflicts with other applications running on your device.
   - Look for error messages in the Android Studio console and try to resolve them based on the information provided.
