from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.core import serializers
from centennialmeetup.models import *
import json

@csrf_exempt
def register(request):
    if request.method == "POST":
        input = request.body.split("&")
        data = {}
        for i in input:
            object = i.split("=")
            data[object[0]] = object[1]
        print data
        
        centennialId = data["centennialid"]
        firstname = data["firstname"]
        lastname = data["lastname"]
        email = data["email"]
        dateofbirth = data["dateofbirth"]
        hobby = data["hobby"]
        password = data["password"]
        program = data["program"]
        department = data["department"]
        campus = data["campus"]
        phone = data["phone"]
        profession = data["profession"]
        
        user_object = User(centennialid = int(centennialId), firstname = firstname, lastname = lastname, emailid = email, dateofbirth = dateofbirth, hobby = hobby, password = password, program = program, department = department, campus = campus, phone = phone, profession = profession)
        flag = user_object.save()
        
        response = HttpResponse()
        response.write("User registered")
        response.status_code = 200
        return response        

    else:
        response = HttpResponse()
        response.write("Invalid method.")
        response.status_code = 500
        return response
    

@csrf_exempt    
def login(request):
    centennialid = request.GET.get("centennialid");
    password = request.GET.get("password");
    
    user_object = User.objects.get(centennialid = centennialid)
    if user_object:
        if not user_object.password == password:
            response = HttpResponse()
            response.write("Incorrect password")
            response.status_code = 200
            return response 
        
        else:
            response = HttpResponse()
            response.write("Logged In")
            response.status_code = 200
            return response 
    else:
        response = HttpResponse()
        response.write("No user found")
        response.status_code = 200
        return response         
    
@csrf_exempt
def getprofile(request):
    centennialid = request.GET.get("centennialid")
    
    user_object = User.objects.filter(centennialid = int(centennialid))
    jsonData = user_object.values()[0]
    data = json.dumps(jsonData)
    return HttpResponse(data)


@csrf_exempt
def search(request):
    argument1 = request.GET.get("arg1","")
    argument2 = request.GET.get("arg2","")
    column_name = request.GET.get("columnname","")
    
    if (argument2 == "") and (column_name == ""):
        users = User.objects.filter(firstname = argument1)
    else:
        users = User.objects.filter(firstname = argument1, **{column_name: argument2})
    
    if users:
        jsonData = users.values()[0]
        data = json.dumps(jsonData)
        return HttpResponse(data)
    else:
        response = HttpResponse("Result not found")
        response.status_code = 500
        return response
        
        
        
@csrf_exempt
def getallusers(request):
    users = User.objects.all()
    if users:
        result = users.values()
        #jsonData = serializers.serialize("json",list(result),fields = ("centennialid","firstname","email"))
        arrayData = []
        for r in result:
            arrayData.append(r)
        jsonData = {}
        jsonData["data"] = arrayData
        data = json.dumps(jsonData)
        return HttpResponse(data)
    else:
        response = HttpResponse("Result not found")
        response.status_code = 500
        return response
    

@csrf_exempt
def addfriend(request):
    input = request.body.split("&")
    data = {}
    for i in input:
        object = i.split("=")
        data[object[0]] = object[1]
    print data
    
    user_name = data["otherusersname"]
    centennial_id = data["centennialid"]
    
    user_object = User.objects.get(centennialid = int(centennial_id))
    user_id = user_object.centennialid
    
    user_object1 = User.objects.get(firstname = user_name)
    user_id1 = user_object1.centennialid
    
    frnd_object = Friends(user_id = int(user_id), user_id1 = int(user_id1))
    flag = frnd_object.save()

    response = HttpResponse()
    response.write("Friend added")
    response.status_code = 200
    return response        

@csrf_exempt
def getallfriends(request):
    centennial_id = request.GET.get("centennialid","")
    
    frnds = Friends.objects.filter(user_id = int(centennial_id))
    friendsjson = frnds.values_list()
    print friendsjson
    for f in friendsjson:
        print f
    #if users:
    #    jsonData = users.values()[0]
    #   data = json.dumps(jsonData)
    #    return HttpResponse(data)
    #else:
    #    response = HttpResponse("Result not found")
    #    response.status_code = 500
    #    return response
    