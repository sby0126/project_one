<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>장바구니</title>
  <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"
    integrity="sha512-8bHTC73gkZ7rZ7vpqUQThUDhqcNFyYi2xgDgPDHc+GXVGHXq+xPjynxIopALmOPqzo9JZj0k6OqqewdGO3EsrQ=="
    crossorigin="anonymous" />
  <script src="https://kit.fontawesome.com/a99df0f94f.js" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/underscore@1.11.0/underscore-min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"
    integrity="sha512-8bHTC73gkZ7rZ7vpqUQThUDhqcNFyYi2xgDgPDHc+GXVGHXq+xPjynxIopALmOPqzo9JZj0k6OqqewdGO3EsrQ=="
    crossorigin="anonymous" />
  <style>
    body {
      background: #F3F7FA;
    }

    .my-container {
      width: 100vw;
      height: 100vh;
      margin-top: 6em;
    }
  </style>
</head>

<body>
  <div class="my-container">
    <div class="ui container">

      <div class="ui divided items">
        <div class="item">
          <div class="image">
            <img src="/images/item/M/B/00b7173d5d533a46e5e18604d9920f8f.gif">
          </div>
          <div class="content">
            <a class="header">My Neighbor Totoro</a>
            <div class="meta">
              <span class="cinema">IFC Cinema</span>
            </div>
            <div class="description">
              <p></p>
            </div>
            <div class="extra">
              <div class="ui right floated primary button">
                Buy tickets
                <i class="right chevron icon"></i>
              </div>
              <div class="ui label">Limited</div>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="image">
            <img src="/images/item/M/B/00b7173d5d533a46e5e18604d9920f8f.gif">
          </div>
          <div class="content">
            <a class="header">Watchmen</a>
            <div class="meta">
              <span class="cinema">IFC</span>
            </div>
            <div class="description">
              <p></p>
            </div>
            <div class="extra">
              <div class="ui right floated primary button">
                Buy tickets
                <i class="right chevron icon"></i>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>

  </div>
  </div>
</body>

</html>