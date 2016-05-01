// using Async
private String makePostRequest() {
    HttpClient httpClient = new DefaultHttpClient();
    
    // URL của trang web nhận request
    HttpPost httpPost = new HttpPost("http://khoapham.vn/post.php");
    
    // Các tham số truyền
    List nameValuePair = new ArrayList(2);
    nameValuePair.add(new BasicNameValuePair("so1", "111"));
    nameValuePair.add(new BasicNameValuePair("so2", "222"));
    
    //Encoding POST data
    try {
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }

    String kq = "";
    try {
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        kq = EntityUtils.toString(entity);
    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return kq;
}
