#!/bin/sh
kill -9 $(jps | grep govtrial | awk '{print $1}')