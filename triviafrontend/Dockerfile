FROM node:20 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

FROM nginx:alpine
COPY --from=builder /app/dist/triviafrontend /usr/share/nginx/html
EXPOSE 4200
RUN sed -i -e 's/80/4200/g' /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]
