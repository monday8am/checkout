# README

## Challenge

- Implement an app where a user can pick products from a list and checkout them to get the resulting price. No need to implement any real payment system, just a fake feedback about the payment has been completed.
- The discounts can change in the future, depending on the year season we apply different ones.
- We would like to show users what discounts have been applied in their purchase.

## Comments

1. **Business Logic:** An interesting point is the data class `Discount`, introduced in order to manage the discounts in a more agile way. Although the discount logic is hard to describe using plain data, is possible to classify and describe them, defining a target product, a new price or even a time frame to be applied (not implemented). The discount logic is statically defined inside the app and applied according to the discount info. Check Discount.kt file.

2. **Architecture:** I have decided to use an unidirectional architecture like _MVI_ or _Redux_. Although these kind of architectures have many advantages like testing, predictability and inmutable functions, they should be seamlessly integrated with the Android Architecture Componentsin order to get the best of both approaches. There are many solutions around this point ([Freeletics/RxRedux](https://github.com/freeletics/RxRedux), [Spotify/Mobius](https://github.com/spotify/mobius), [Groupon/Grox](https://github.com/groupon/grox)) but we opted for a pure Redux implementation [ReKotlin](https://github.com/ReKotlin) that is instantiated at ViewModel level. It lets us to use the ViewModel only to hold everything in memory but manage the state and UI logic in a Redux way. In general terms, the relationship between ViewModels, View and Repository remains the same.

3. **UI:** In this case, LiveData (the Android way to deal with ViewModel input/outputs) is replaced by the Redux way: we have created a UI component that is registered into the ViewModel and receives a customised state subscription and an action dispatcher. It gives us the possibility to test the UI component content against each new state received.

4. **Caching:** Right now the products are downloaded and cached in local DB and the discounts are refreshed every time the app is opened. If there is an error downloading the discounts, no discounts are applied. The selected products for checkout could be cached, but then the product model definition will be more complex and we'll need to recalculate the result with the new product price values and discounts. 

5. **Data layer:** There is a Repository that contains all async methods to get the products (from cache or remote), the discounts(always from remote) and to cache the products once they are downloaded. The repository is inyected and used inside the `Middleware`, another Redux element created to manage side effects and generate new actions from them. The Room component, Retrofit and RxJava are used from the repository.

5. **Testing:** The tests are pretty straightforward: the reducers receive an input (state and action) and will return always the same output (state) because they are pure functions. The ViewModels are just containers and don't have any logic inside. Besides, the views can be tested using a state as input and checking the UI component configuration.

6. **Evolution:** The app can be extended as a normal Android app, adding more ViewModels, defining states and actions per ViewModel and creating UI components to show them.

## Inspiration

- [Netflix reactive approach](https://medium.com/netflix-techblog/making-our-android-studio-apps-reactive-with-ui-components-redux-5e37aac3b244)
- [New Composable Android API](https://blog.karumi.com/android-jetpack-compose-review/)
