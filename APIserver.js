var express = require('express');
var morgan = require('morgan');
var path = require('path');
var Pool = require('pg').Pool;
var crypto = require('crypto');
var bodyParser = require('body-parser');
var session = require('express-session');

var config = {
    user: 'achy08',
    database: 'imad-articles',
    host: "localhost",
    port:'5432',
    password: "xxxx"
};

var app = express();
app.use(morgan('combined'));
app.use(bodyParser.json());
app.use(session({
    secret: 'oneRandomSecretValue',
    cookie: {maxAge: 1000 * 60 * 60 * 24 * 30}
}));

app.get('/', function (req, res) {
  res.send("Android API Server");
});

function hash(input, salt){
    //How to create a hash?
    var hashed = crypto.pbkdf2Sync(input, salt, 10000, 512, 'sha512');
    return ["pbkdf2","10000", salt, hashed.toString('hex')].join('$');
}
app.get('/hash/:input', function(req,res){
    var hashedString = hash(req.params.input, 'this-is-a-random-string');
    res.send(hashedString);
});

var comments = [];
app.get('/submit-comment', function(req,res){
  var comment = req.query.comment;
  comments.push(comment);
  res.send(JSON.stringify(comments));
});

app.post('/new-user', function(req,res){
    //username, password
    //JSON
    var username = req.body.username;
    var password = req.body.password;
    
    //SALTED HASH and add to DB
    var salt = crypto.randomBytes(128).toString();
    var dbString = hash(password, salt);
    pool.query('INSERT INTO "user"(username, password) VALUES ($1, $2)', 
    [username, dbString], function(err, result){
        if (err){
            res.status(500).send(err.toString());
        } else{
            //res.send('New user created:' + username);
	res.setHeader('Content-Type', 'application/json');
	res.send(JSON.parse('{"message" : "User is registered now"}'));

        }  
    });
});

app.post('/new-article', function(req,res){
	var title = req.body.title
	var author = req.body.user
	var date = req.body.date
	var content = req.body.content
	pool.query('INSERT INTO "articles"(title, author, date, content) VALUES ($1, $2, $3, $4)',

app.post('/login', function(req, res){
    var username = req.body.username;
    var password = req.body.password;

    pool.query('SELECT * FROM "user" WHERE username = $1', [username], function(err, result){
        if (err){
            res.status(500).send(err.toString());
        } else{
            if(result.rows.length === 0){
                res.sendStatus(403).send('invalid user');
            } else{
                //Match password
                var dbString = result.rows[0].password;
                var salt = dbString.split('$')[2];

                //Create hash based on the submitted password and original salt to compare with salted hash in DB
                var hashedPassword = hash(password, salt);
                if (hashedPassword === dbString){
                    //Set session
                    req.session.auth = {userId: result.rows[0].id}
                    //res.send('Successful Login');
                    res.setHeader('Content-Type', 'application/json');
	    res.send(JSON.parse('{"message" : "You are now logged in"}'));

                } else{
                    res.send(403).send('wrong info')
                }
            }
        }  
    });
});

app.get('/check-login', function (req,res){
    if (req.session && req.session.auth && req.session.auth.userId){
        res.send('Logged in with user id:' + req.session.auth.userId.toString());
    } else {
        res.send('No one home')
    }
});

app.get('/logout', function(req, res){
    // end session
    delete req.session.auth;
    res.send('Logged out now')
})

var pool = new Pool(config);

app.get('/get-articles', function(req, res){
   //make a select request
   //return a response with the results
   pool.query('SELECT * from public.articles order by id', function(err, result){
      if (err){
          res.status(500).send(err.toString());
      } else{
          res.setHeader('Content-Type', 'application/json');	
          res.send(JSON.stringify(result.rows));
      }
   });
});

// Do not change port, otherwise your app won't run on IMAD servers
// Use 8080 only for local development if you already have apache running on 80

var port = 80;
app.listen(port, function () {
  console.log(`IMAD course app listening on port ${port}!`);
});
