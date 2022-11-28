RESTfull - Asynchronous operation

We have 2 controllers: TourController and ConfirmationController

TourController has GET link /tour that gives all tours:
![tour_list.png](results/tour_list.png)

1. For example, we can delete tour with id "wntr" (I used non-integer id's):
![tour_delete.png](results/tour_delete.png)
2. After clicking execute, delete is waiting for confirmation. I used 1 min "Thread.sleep".
3. Within 1 minute we need to confirm this operation.
4. We use ConfirmationController for confirmation. We need to send POST request with our tour id "wntr":
![confirmation](results/confirmation.png)
5. We got "Transaction confirmed" response which means that our operation was successful.
6. We got "OK" response which means that tour was successfully deleted 
![tour_deleted](results/tour_deleted.png)
7. Now we don't have "wntr" tour:
![tour_list_after](results/tour_list_after.png)