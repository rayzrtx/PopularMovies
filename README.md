# Popular Flix
This project combines Part 1 and Part 2 of the Popular Movies project of the Google/Udacity Nanodegree course. Upon launch, user will be presented with a grid layout of the most popular movies from <a href="https://www.themoviedb.org">The Movie Database</a> API. User will be able to update visible list of movies via a menu to show Most Popular, Top Rated, or Favorites, which are movies that the user has marked as a favorite. 
<p>Upon clicking a movie poster, user is taken to a details screen with various details of clicked movie.</p>
<p>Included in details screen is a Trailers section with clickable trailer images that open up the selected trailer in the YouTube app or the <a href="https://www.youtube.com">YouTube</a> website and a Reviews section which contains The Movie DB user reviews of movie.</p>
<h2>Common Project Requirements</h2>
<p>App is written solely in the Java Programming Language.</p>
<p>App conforms to common standards found in the Android Nanodegree General Project Guidelines.</p>
<p>App utilizes stable release versions of all libraries, Gradle, and Android Studio.</p>
<h2>User Interface - Layout</h2>
<p>UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.</p>
<p>Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.</p>
<p>UI contains a screen for displaying the details for a selected movie.</p>
<p>Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.</p>
<p>Movie Details layout contains a section for displaying trailer videos and user reviews.</p>
<h2>User Interface - Function</h2>
<p>When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.</p>
<p>When a movie poster thumbnail is selected, the movie details screen is launched.</p>
<p>When a trailer is selected, app uses an Intent to launch the trailer.</p>
<p>In the movies detail screen, a user can tap a button, in this case a heart icon, to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.</p>
<h2>Network API Implementation</h2>
<p>In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.</p>
<p>App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.</p>
<p>App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.</p>
<h2>Data Persistence</h2>
<p>The titles and IDs of the user’s favorite movies are stored using Room.</p>
<p>Data is updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.</p>
<p>When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the database.</p>
<h2>Android Architecture Components</h2>
<p>If Room is used, database is not re-queried unnecessarily. LiveData is used to observe changes in the database and update the UI accordingly.</p>
<p>If Room is used, database is not re-queried unnecessarily after rotation. Cached LiveData from ViewModel is used instead.</p>
<h2>Main Concepts used</h2>
<ul><li>RecyclerView</li>
<li>Android Architecture Components (Room library, LiveData, ViewModel)</li>
<li>AsyncTask</li>
<li>JSON</li>
<li>API implementation</li>
<li>Intents</li></ul>
<h2>Libraries used</h2>
<ul><li>Picasso</li>
<li>Room</li></ul>
<h2>Notes</h2>
<p>In order to use the app, you will need to use your own <a href="https://www.themoviedb.org"> The Movie Database</a> API key. It can be entered in the URLConstant class</p>
<h2>Acknowledgements</h2>
<p>All movie data retrieved via The Movie Database API</p>
<p>Various <a href="https://stackoverflow.com/">Stack Overflow</a> posts</p>
<p><a href="https://www.udacity.com">Udacity</a></p>
<h2>Screenshots</h2>
<p align="center"><img src="https://user-images.githubusercontent.com/22454498/43995304-0121448c-9d71-11e8-8e90-dcafff835d82.png" alt="Main Screen screenshot">
<img src="https://user-images.githubusercontent.com/22454498/43995363-ff01487c-9d71-11e8-84a5-b2527fa9557d.png" alt="Main screen with Menu options visible screenshot"></p>
<p align="center"><img src="https://user-images.githubusercontent.com/22454498/43995362-fef09234-9d71-11e8-9bf3-25a5422ce942.png"></p>
<p align="center"><img src="https://user-images.githubusercontent.com/22454498/43995361-fede9160-9d71-11e8-8132-44eeef006089.png">
  <img src="https://user-images.githubusercontent.com/22454498/43995360-feceea62-9d71-11e8-934b-99f823b35bf1.png"></p>
 <p align="center"><img src="https://user-images.githubusercontent.com/22454498/43995358-feab64a2-9d71-11e8-8ca0-12ad7867380f.png">
  <img src="https://user-images.githubusercontent.com/22454498/43995359-febc6c2a-9d71-11e8-8b5f-46e256995427.png"></p>
