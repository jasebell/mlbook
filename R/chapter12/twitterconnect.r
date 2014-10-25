library(twitteR)
cred <- OAuthFactory$new(consumerKey="xxxxxxxxxxxxx",
 consumerSecret="xxxxxxxxxxxx",
 requestURL="http://api.twitter.com/oauth/request_token",
 accessURL="http://api.twitter.com/oauth/access_token",
 authURL="http://api.twitter.com/oauth/authorize")
download.file(url="http://curl.haxx.se/ca/cacert.pem", destfile="cacert.pem")
cred$handshake(cainfo="cacert.pem")
