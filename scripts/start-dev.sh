#!/bin/bash
echo 'Starting DEV environment...'
docker compose --env-file ../.env.dev up
