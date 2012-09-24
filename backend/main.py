import webapp2
from google.appengine.ext import db
from google.appengine.api import users
import urllib
import time

class MainPage(webapp2.RequestHandler):
    def get(self):
        user = users.get_current_user()

        if user:
            self.response.headers['Content-Type'] = 'text/plain'
            self.response.out.write('Hello, ' + user.nickname())
        else:
            self.redirect(users.create_login_url(self.request.uri))

class ImageShowPage(webapp2.RequestHandler):
    def get(self):
        user = users.get_current_user()
        if not user:
            self.redirect(users.create_login_url('/image'))

        user_id = user.user_id()
        userimages = db.GqlQuery("SELECT *"
                                 "FROM UserImage "
                                 "WHERE ANCESTOR IS :1 "
                                 "ORDER BY date DESC LIMIT 10",
                                 userimage_key(user_id))
        self.response.headers['Content-Type'] = 'application/json'
        total = userimages.count()
        self.response.write('{"status": "OK", "total": %d, "img_ids":[' % total)
        count = 0
        for userimage in userimages:
            self.response.write('%s' % userimage.id)
            count += 1
            if count < total:
                self.response.write(',')

        self.response.write(']}')

class ImagePage(webapp2.RequestHandler):
    def get(self):
        user = users.get_current_user()
        if not user:
            self.redirect(users.create_login_url('/image'))

        img_id = self.request.get('img_id')
        user_id = user.user_id()
        if not img_id:
            userimages = db.GqlQuery("SELECT *"
                                     "FROM UserImage "
                                     "WHERE ANCESTOR IS :1 "
                                     "ORDER BY date DESC LIMIT 10 ",
                                     userimage_key(user_id))
            self.response.headers['Content-Type'] = 'application/json'
            total = userimages.count()
            self.response.write('{"status": "OK", "total": %d, "img_ids":[' % total)
            count = 0
            for userimage in userimages:
                self.response.write('%s' % userimage.id)
                count += 1
                if count < total:
                    self.response.write(',')

            self.response.write(']}')
        else:
            results = db.GqlQuery("SELECT * "
                                    "FROM UserImage "
                                    "WHERE ANCESTOR IS :1 AND id = :2 ",
                                    userimage_key(user_id),
                                    img_id)
            for userimage in results:
                self.response.headers['Content-Type'] = 'image/jpeg'
                self.response.out.write(userimage.data)
                return 





class UserImage(db.Model):
    id = db.StringProperty()
    data = db.BlobProperty()
    date = db.DateTimeProperty(auto_now_add=True)

def userimage_key(user_id = None):
    return db.Key.from_path('userimage', user_id or 'default_user_id')

class ImageUploadPage(webapp2.RequestHandler):
    def get(self):
        self.response.out.write("""
                  <html>
                  <body>
                  <form action="/image/upload" enctype="multipart/form-data" method="post">
                    <div><label>Image file:</label></div>
                    <div><input type="file" name="img"/></div>
                    <input type="submit" value="upload" />
                  </form>
                  </body>
                  </html>""")

    def post(self):
        user = users.get_current_user()
        if not user:
            self.redirect(users.create_login_url(self.request.uri))

        user_id = user.user_id()
        userimage = UserImage(parent=userimage_key(user_id))
        userimage.id = time.time().__str__()
        data = self.request.get('img')
        userimage.data = db.Blob(data)
        userimage.put()
        self.redirect('/image')


class ImageDeletePage(webapp2.RequestHandler):
    def get(self):
        self.response.out.write("""
            <html>
            <body>
            <form action="/image/delete" method="post">
                <input type="submit" value="delete all" />
            </form>
            </body>
            </html>
                                """)

    def post(self):
        user = users.get_current_user()
        if not user:
            self.redirect(users.create_login_url('/image'))

        user_id = user.user_id()

        userimages = db.GqlQuery("SELECT *"
                                 "FROM UserImage "
                                 "WHERE ANCESTOR IS :1 "
                                 "ORDER BY date DESC LIMIT 10",
                                 userimage_key(user_id))
        for userimage in userimages:
            userimage.delete()
        self.redirect('/image/show')



app = webapp2.WSGIApplication([('/', MainPage),
                               ('/image/show', ImageShowPage),
                               ('/image/upload', ImageUploadPage),
                               ('/image/delete', ImageDeletePage),
                               ('/image', ImagePage)], debug=True)

