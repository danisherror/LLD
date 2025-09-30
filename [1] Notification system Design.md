

----
# [Design a Scalable Notification Service - System Design Interview](https://blog.algomaster.io/p/design-a-scalable-notification-service)  

---
## 1. Requirement Gathering

- Here we can see what are the basic requirement for a notification system.
- It is good for a basic notification system.
- It has divided the requirement in functional and non-functional requirements so that during the interview we can focus on the functionality rather than scaleability.
- In its functional requirements it include all important functionality that a notification system should have
- I think this section is needed because before diving into the questions we should know what are the requirements and the functionality of the system before we built it. By taking the requirement in the initial phase of the project we can see in what direction we have to move and we don’t have to change our design in future.

## 2. Scale Estimation

- Here the author talks about the number of notification that we have to send per day or at peak time and the storage that is needed to store user data and notification storage per day
- I think this section is needed because we have to see what is the number of user's and notifications we have to store and send, by doing this we can see do I have to make my design a simple design or complex design that can easily manage large number of users and notifications

## 3. High Level Design

- what I can say by seeing the diagram
- Services will ask the notification system to send the notification to the user
- Here I am saying services because we have to create a backend that can easily integrate with any type of product. 
- That product can be a e-commerce site or a payment gateway etc.
- Services will come to load balancer before reaching the notification services.
- Load balancer will balance the request and send that request to the notification services
- Notification services
- Before doing anything, notification service will validate that request to see if it is a valid request.
- Before sending notifications to the user, it will first get user preferences from the DB.




[https://medium.com/@tanushree2102/designing-a-scalable-notification-system-from-hld-to-lld-e2ed4b3fb348](https://medium.com/@tanushree2102/designing-a-scalable-notification-system-from-hld-to-lld-e2ed4b3fb348)  
[https://medium.com/@in10se/system-design-interview-question-3-realtime-notification-service-14fe1dec8e2a](https://medium.com/@in10se/system-design-interview-question-3-realtime-notification-service-14fe1dec8e2a)  
[https://bytebytego.com/guides/how-does-a-typical-push-notification-system-work/](https://bytebytego.com/guides/how-does-a-typical-push-notification-system-work/)  
[https://www.codekarle.com/system-design/Notification-system-design.html](https://www.codekarle.com/system-design/Notification-system-design.html)  
[https://www.notificationapi.com/blog/notification-service-design-with-architectural-diagrams](https://www.notificationapi.com/blog/notification-service-design-with-architectural-diagrams)  
[https://www.linkedin.com/pulse/design-notification-system-saral-saxena--8hj9c/](https://www.linkedin.com/pulse/design-notification-system-saral-saxena--8hj9c/)  
[https://blog.bitsrc.io/notifications-system-design-how-we-integrated-it-into-our-infrastructure-f93f279c18a0](https://blog.bitsrc.io/notifications-system-design-how-we-integrated-it-into-our-infrastructure-f93f279c18a0)  
[https://blog.devops.dev/building-a-flexible-and-scalable-notification-system-design-and-implementation-eef601f22518](https://blog.devops.dev/building-a-flexible-and-scalable-notification-system-design-and-implementation-eef601f22518)  
[https://www.magicbell.com/blog/notification-system-design](https://www.magicbell.com/blog/notification-system-design)

