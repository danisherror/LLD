

- Read 6. and 1 for best explaination
----
# [ 1. Design a Scalable Notification Service - System Design Interview](https://blog.algomaster.io/p/design-a-scalable-notification-service)  

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

![High Level Design of Notification System](https://substackcdn.com/image/fetch/$s_!FCvc!,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2Fd2f3b21f-5366-4e42-b868-c3df3548cf62_1697x944.png)

- what I can say by seeing the diagram
- Services will ask the notification system to send the notification to the user
	- Here I am saying services because we have to create a backend that can easily integrate with any type of product. 
	- That product can be a e-commerce site or a payment gateway etc.
- Services will come to load balancer before reaching the notification services.
	- Load balancer will balance the request and send that request to the notification services
- Notification services
	- Before doing anything, notification service will validate that request to see if it is a valid request.
	- Before sending notifications to the user, it will first get user preferences from the DB.
		- This is important because some user have preference that they don't want to receive the notifications or don't want notification more that 2 per day.
- Scheduler Service
	- Here think of water reminders, we have set give notification after every 40 mins saying take rest and drink water.
	- Now after 40 mins has passed, notification service will take the information from the DB and send the notification.
- Notification services uses queues to send notification information to the channels (email, push, sms ).
	- Queue is used as we can define some set of rule or give priority to the message etc.
- Channel Processors.
	- The role of channel Processors is to take the notifcation information from the queue and act on them accordingly.
- Database:
	- I can think of multiple things that is needed to store in the database
		- User information
		- Notification information
		- Large files
		- Logs / Notification status
		- User preference data
## 4. Bottlenecks

- Handling failure and retries
	- Lets say a notification to user failed, but it is the bill of his purchase. Since, it is an important notification we have to try to resend to the user.
	- We can try to re-send the notification by channel processor.
	- After enough tries, that notification should be tried after some waiting time. Maybe some dead queue.
- Horizontal scalability
	- Mostly scalability is needed for load balancer, notification service, notification queue, and channel processors when our user grows.
- Sharding and Partitioning
	- It is used when our user database and notification logs has become big
- Monitoring and logging 
	- **Centralized Logging**: Use tools like **ELK Stack** or **Prometheus/Grafana** to collect logs from various components and monitor the health of the system.
	- **Alerting**: Set up alerts for failures (e.g., notification delivery failure rates exceed a threshold).
	- **Metrics**: Track metrics like success rate, failure rate, delivery latency, and throughput for each channel.
- Archiving Old Data

----
# [# 2. Designing a Scalable Notification System: A Deep Dive](https://medium.com/@rahulgargblog/designing-a-scalable-notification-system-a-deep-dive-3f0fc9161e21)
---
- It is mostly similar to 1st heading

![High levle system design](https://miro.medium.com/v2/resize:fit:1400/format:webp/1*ehWkBafOxyRpybcqVx9oOg.png)

- It can handle more notifications.
- Keeping this article to learn about some technology used in this article
---
#  [ 3. https://medium.com/@tanushree2102/designing-a-scalable-notification-system-from-hld-to-lld-e2ed4b3fb348](https://medium.com/@tanushree2102/designing-a-scalable-notification-system-from-hld-to-lld-e2ed4b3fb348)  

---
# [4. Realtime Notification Service](https://medium.com/@in10se/system-design-interview-question-3-realtime-notification-service-14fe1dec8e2a)  

---
- I didn't find the theory any helpfull, but the high level design is shown in detail

![High Level Design](https://miro.medium.com/v2/resize:fit:4800/format:webp/1*DwJ6XWmff5aJHntqMkpxng.jpeg)

---
# [5. Explore the architecture of a typical push notification system.](https://bytebytego.com/guides/how-does-a-typical-push-notification-system-work/)  

---
![High Level Design](https://assets.bytebytego.com/diagrams/0042-design-a-notification-push-system.png)

The diagram above shows the architecture of a notification system that covers major notification channels:

- **In-App notifications**
- **Email notifications**
- **SMS and OTP notifications**
- **Social media pushes**

Let’s walk through the steps.

- Steps 1.1 and 1.2 - The business services send notifications to the notification gateway. The gateway can handle two modes: one mode receives one notification each time, and the other receives notifications in batches.
- Steps 2, 2.1, and 2.2 - The notification gateway forwards the notifications to the distribution service, where the messages are validated, formatted, and scheduled based on settings. The notification template repository allows users to pre-define the message format. The channel preference repository allows users to pre-define the preferred delivery channels.
- Step 3 - The notifications are then sent to the routers, normally message queues.
- Step 4 - The channel services communicate with various internal and external delivery channels, including in-app notifications, email delivery, SMS delivery, and social media apps.
- Steps 5 and 6 - The delivery metrics are captured by the notification tracking and analytics service, where the operations team can view the analytical reports and improve user experiences.
---
# [6. Notification Service Design](https://www.codekarle.com/system-design/Notification-system-design.html)  

---
- the high level design is shown in detail
- Best explanation of the design
![High Level Design](https://www.codekarle.com/images/Notification.png)

---
# [ 6. https://www.notificationapi.com/blog/notification-service-design-with-architectural-diagrams](https://www.notificationapi.com/blog/notification-service-design-with-architectural-diagrams) 

---


---
# [ 7. https://www.linkedin.com/pulse/design-notification-system-saral-saxena--8hj9c/](https://www.linkedin.com/pulse/design-notification-system-saral-saxena--8hj9c/)  

---



# [8. https://blog.bitsrc.io/notifications-system-design-how-we-integrated-it-into-our-infrastructure-f93f279c18a0](https://blog.bitsrc.io/notifications-system-design-how-we-integrated-it-into-our-infrastructure-f93f279c18a0)  

---

# [9 . https://blog.devops.dev/building-a-flexible-and-scalable-notification-system-design-and-implementation-eef601f22518](https://blog.devops.dev/building-a-flexible-and-scalable-notification-system-design-and-implementation-eef601f22518)  


---


