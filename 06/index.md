RESTfull - Asynchronous operation

We have 2 controllers: TourController and ConfirmationController

TourController has GET link /tour that gives all tours:
![tour_list.png](results/tour_list.png)

1. For example, we can delete tour with id "cstl" (I used non-integer id's):
2. After clicking execute, we got 202 status (Accepted) with location of status resource
![](results/1.png)
3. Delete task in progress
![](results/2.png)
4. After some time delete task is successfully completed
   ![](results/3.png)