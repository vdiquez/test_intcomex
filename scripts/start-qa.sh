#!/bin/bash
echo 'Starting QA environment...'
docker compose --env-file ../.env.qa up
