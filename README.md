# TestTask

Implementation Requirements / Guidelines:
1. User will be able to enter a search phrase. The search action should be performed when the user clicks on ‘search’ button in the keyboard or ‘search’ button.
2. Use the following endpoint to retrieve the list of news items for the search query:
https://www.tipranks.com/api/news/posts
Parameters:
o page={page:Int, starting at 1}
o per_page=20 [no need to change this] 
o search={search_query:String}
3. Implement a paging mechanism to load the next pages as the user scrolls down.
4. Display a loader while loading the next page of news articles.
5. Clicking on a news item should open an internal screen that will display the article using
a WebView component.
6. Insert “Promotion” bar after article #3, #13, #23, #33, etc. Clicking on “Go to Promotion”
shouldopenhttps://www.tipranks.com/ indevicebrowser
7. Bonus: Footer list item – show loader while loading next page OR “end of list” when no
more pages (remove loader in item [4] if implemented)
Final Tip: Implement android best-practice architecture where possible: Material Design, MVVM, DiffUtill, Navigation component, Paging-library, etc.
