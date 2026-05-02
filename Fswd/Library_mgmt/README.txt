================================================================
        LIBRARY MANAGEMENT API - README
================================================================

A RESTful API built with Node.js, Express, and MongoDB (Mongoose)
for managing a library system — supports book inventory,
reservations, borrowing, and returns.

----------------------------------------------------------------
TECH STACK
----------------------------------------------------------------
- Node.js
- Express v5
- MongoDB + Mongoose
- Body-Parser

----------------------------------------------------------------
PROJECT STRUCTURE
----------------------------------------------------------------
library_mgmt/
  models/
    Book.js
  server.js
  package.json
  README.txt

----------------------------------------------------------------
SETUP & RUN
----------------------------------------------------------------
Prerequisites:
  - Node.js installed
  - MongoDB running locally on port 27017

Install dependencies:
  npm install

Start the server:
  node server.js

Server runs at: http://localhost:3000



----------------------------------------------------------------
TESTING WITH CURL
----------------------------------------------------------------

Linux/Mac:
  # Add a book
  curl -s -X POST http://localhost:3000/admin/add-book \
    -H "Content-Type: application/json" \
    -d '{"title":"The Alchemist","author":"Paulo Coelho","category":"Fiction","totalCopies":3}' \
    | python3 -m json.tool

  # Get all books
  curl -s http://localhost:3000/books | python3 -m json.tool

Windows CMD:
  curl -s -X POST http://localhost:3000/admin/add-book ^
    -H "Content-Type: application/json" ^
    -d "{\"title\":\"The Alchemist\",\"author\":\"Paulo Coelho\",\"category\":\"Fiction\",\"totalCopies\":3}" ^
    | python -m json.tool

  curl -s http://localhost:3000/books | python -m json.tool

----------------------------------------------------------------
BOOK SCHEMA
----------------------------------------------------------------

Field            Type       Description
---------------  ---------  ----------------------------------
title            String     Book title (required)
author           String     Author name (required)
category         String     Genre/category
totalCopies      Number     Total copies in library (required)
availableCopies  Number     Copies currently available
reservedBy       [String]   List of users who reserved
borrowedBy       [String]   List of users who borrowed

----------------------------------------------------------------
LICENSE
----------------------------------------------------------------
ISC

================================================================
