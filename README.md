## Create redis-sentinel cluster 

Using: https://github.com/bitnami/charts/tree/main/bitnami/redis

helm upgrade --install redis-sentinel --namespace redis-sentinel --create-namespace  --set sentinel.enabled=true,volumePermissions.enabled=true,replica.replicaCount=3,auth.enabled=false,master.livenessProbe.enabled=true,master.persistence.size=10Gi,replica.persistence.size=10Gi oci://registry-1.docker.io/bitnamicharts/redis

### Testing: local using k8s
kubectl port-forward --namespace redis-sentinel svc/redis-sentinel 26379:26379 &

## Make the image
docker build -t redis-sentinel .

## Testing: Run container
docker run -it -p 8080:8080 redis-sentinel 

## Upload.
docker login
```
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: chuchip
Password:
Login Succeeded

Logging in with your password grants your terminal complete access to your account.
For better security, log in with a limited-privilege personal access token. Learn more at https://docs.docker.com/go/access-tokens/
```

docker tag redis-sentinel docker.io/chuchip/redis-sentinel:1.0

docker push docker.io/chuchip/redis-sentinel:1.0

# Apply to k8s.

kubectl delete deployment/back-redis-sentinel -n redis-sentinel 
kubectl apply -f back.yml -n redis-sentinel

