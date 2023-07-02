## Create redis-sentinel cluster 

Using: https://github.com/bitnami/charts/tree/main/bitnami/redis

    helm upgrade --install redis-sentinel --namespace redis-sentinel --create-namespace  --set sentinel.masterSet=sentinelMaster,sentinel.enabled=true,volumePermissions.enabled=true,replica.replicaCount=3,auth.enabled=false,master.livenessProbe.enabled=true,master.persistence.size=10Gi,replica.persistence.size=10Gi oci://registry-1.docker.io/bitnamicharts/redis

### Testing: local using k8s
> kubectl port-forward --namespace redis-sentinel svc/redis-sentinel 26379:26379 &

## Make the image
> docker build -t redis-sentinel .

## Testing: Run container
> docker run -it -p 8080:8080 redis-sentinel # in local 

## Upload.
```
$ docker login

Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: chuchip
Password:
Login Succeeded

Logging in with your password grants your terminal complete access to your account.
For better security, log in with a limited-privilege personal access token. Learn more at https://docs.docker.com/go/access-tokens/
```

    $ docker tag redis-sentinel docker.io/chuchip/redis-sentinel:1.0

    $ docker push docker.io/chuchip/redis-sentinel:1.0

## Apply to k8s.

    $ kubectl delete deployment/back-redis-sentinel -n redis-sentinel # (Only if it was previously deployed)

    $ kubectl apply -f back.yml -n redis-sentinel

    $ kubectl port-forward --namespace redis-sentinel svc/back-redis-sentinel 8080:8080

## Testing

You can use swagger in http://localhost:8080/swagger-ui.html or you can use curl as I show below:

#### Getting value of key: 1 when it doesn't exist.
```
$ curl http://localhost:8080/redis/1 ; echo ""

Not found key: 1
```

#### Putting the value of key: 1 to "Value1"
```
$ curl -X PUT http://localhost:8080/redis/1/value1; echo ""

value1
```

#### Getting value of key: 1 when it  exists.
```
$ curl http://localhost:8080/redis/1 ; echo "" 

value1
```