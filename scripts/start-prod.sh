#!/bin/bash
echo 'Starting PROD environment...'
docker compose --env-file ../.env.prod up -d
