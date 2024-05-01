This repository houses a comprehensive client-server application tailored for managing educational course applications, built primarily for the Android platform.

Client-side features include robust user authentication and account creation utilizing Firebase's "email-password" authentication mechanism. Stringent validation checks, including email validity and password complexity, ensure data integrity. Upon account creation, user details are securely stored in the Firebase "users" collection. Users can seamlessly apply for available courses, with their application information seamlessly recorded in the "applicants" collection.

The user interface seamlessly guides users through the application process, starting with a login page and progressing to a course selection interface. Upon successful authentication, users can browse available courses and apply with ease.

Administrators benefit from a dedicated interface enabling them to efficiently monitor user activities. They can view registered users along with their application details, empowering them to track user engagement effectively.

Additionally, administrators can communicate directly with users via phone or text messages within the application, fostering seamless interaction and facilitating quick responses to user inquiries or the provision of additional information.

Special administrative privileges are safeguarded with a specific authentication mechanism, requiring administrators to input predetermined credentials {email: "admin@gmail.com", password: "adminA77"}. Upon successful authentication, administrators are directed to a dedicated interface providing access to administrative functionalities.
