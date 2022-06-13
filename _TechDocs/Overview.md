# Shopping Cart Akka architecture #

Shopping Cart system. When complete, end users will be able to add items to a cart and checkout, creating an order. As shown in the following illustration, the system includes three services that use [Akka gRPC](https://developer.lightbend.com/docs/akka-platform-guide/concepts/akka-grpc.html) and Kafka as transport mechanisms: Cart, Order, and Analytics.

![System](../TechDocs/assets/Screenshot%202022-05-25%20at%2010.30.09.png)

# Example overview #
Each user’s cart is represented by a Cart Entity. Cart state is persisted using [Event Sourcing](https://developer.lightbend.com/docs/akka-platform-guide/concepts/event-sourcing.html): When a user updates their cart, the Entity persists events in an Event Journal database. Using [Command Query Responsibility Segregation (CQRS)](https://developer.lightbend.com/docs/akka-platform-guide/concepts/cqrs.html), which separates read and write responsibility, [Akka Projections](https://doc.akka.io/docs/akka-projection/current/?_ga=2.5168560.22567982.1653492027-1846188539.1653492027) new tab provide the data necessary for the Order and Analytics services.

The PopularityProjection uses the events from all shopping carts to store a representation in the database to answer queries of how popular the items are.

# Local Dev #
[Running in Local](https://developer.lightbend.com/docs/akka-platform-guide/microservices-tutorial/template.html)