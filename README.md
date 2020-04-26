# elevate-api

This project is the backend core infrastructure for #eu-vs-virus hackathon ELEVATE app proposal.

ELEVATE aims to be a solution for sport couch freelancers to be able of manage virtual streamed activities while configuring customer memberships.

In this draft version only several functionalities have been implemented check 'Project features' section

The frontend is provided by another project [native.elevate](https://github.com/leiteszeke/native.elevate.com)

## Requirements 

JDK 11

## building 

build the jar with `./gradlew build -x test`
And execute the output 
`java -Dspring.profiles.active=local -jar build/libs/*.jar`

You can also user bootrun passing SPRING_PROFILES_ACTIVE env. variable.

## Local env

Local application environment has been setup to use a heroku mLab MongoDB instance and a personal account for image storage. Probably the keys will change in future iterations.

## Project features

The core project features are:
* Authentication & Register based on email (no social network SSO at this point), for two types of roles, Freelancers and Customers
* Freelancers profile management
  * A basic API has been exposed to manipulate profile details
  * Profile pictures can be modified also. (The hosting happens on https://imgbb.com/)
  * A gallery of up to 8 pictures by freelancer is also editable (But no image deletion have been implemented yet)
* Freelancers can manage the active memberships that their customers can engage
  * Freelancers may modify the active subscriptions that customer would be able to acquire in order to access their stream contents.

## Future iterations

This project has been done in two days, meaning that there is a lot of space for improvement and new features. Several of them that come to mind are:
* Technical/Formal perspective:
  * Ton of test coverage
  * General refactors on auth layer
  * Input validation layer for all APIs
* Customer perspective:
  * Profile manipulation
  * Search module for freelancers and activities
  * Ability to purchase memberships
  * Access to protected live sessions based on membership verification
* Freelance perspective:
  * Session Schedule management (including alerting to already subscribed customers)
  * Member referential including all customers with active subscriptions to freelance contents.
  * Finance referential to keep track of benefits.
  
Many more points could appear as the features are endless. 
  * Integration with kafka, 
  * Emailing service
  * Search engine based on Elastic Search
  * Analytics dashboard
  * Marketing tools for freelancers...
  
