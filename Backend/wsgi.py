from flask import Flask, request, Response, jsonify
import json
from bson import json_util
from pymongo import MongoClient

app = Flask(__name__)


@app.route('/')
def home():
    return 'All OKAY!'

@app.route('/user/<string:fname>', methods=['GET'])
def user_search(fname):

    client = MongoClient()


    collection = client.db.user_data

    _data = collection.find({'name': str(fname)})

    ret = json_util.dumps({'users':  _data}, default=json_util.default)
    print ret

    return Response(response=ret,
                    status=200,
                    headers=None,
                    content_type='application/json',
                    direct_passthrough=False)


@app.route('/new/user', methods=['POST'])
def new_user():

    if request.method == 'POST':
        if request.headers['Content-Type'] == 'application/json':
            print "khi"

            client = MongoClient()

            collection = client.db.user_data


            try:
                data = json.loads(request.data)
                print data

            except (ValueError, KeyError, TypeError):
                # Not valid information, bail out and return an error
                return jsonify({'error': 'opps'})

            collection.insert({"name": data['name'], "handle": data['handle'] })

            print collection.count()

            return jsonify({'status': 'successful'})




if __name__ == '__main__':
    app.run()
