const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const Book = require('./models/Book');

const app = express();
app.use(bodyParser.json());

mongoose.connect('mongodb://127.0.0.1:27017/libraryDB')
  .then(() => console.log("MongoDB Connected"))
  .catch(err => console.log(err));

// Admin - Add book
app.post('/admin/add-book', async (req, res) => {
  try {
    const { title, author, category, totalCopies } = req.body;
    const book = new Book({ title, author, category, totalCopies, availableCopies: totalCopies });
    await book.save();
    res.json({ message: "Book added successfully", book });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Admin - Inventory
app.get('/admin/inventory', async (req, res) => {
  try {
    const books = await Book.find();
    res.json(books);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Get all books
app.get('/books', async (req, res) => {
  try {
    const books = await Book.find();
    res.json(books);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Search
app.get('/books/search', async (req, res) => {
  try {
    const { title, category } = req.query;
    const query = {};
    if (title) query.title = new RegExp(title, 'i');
    if (category) query.category = category;
    const books = await Book.find(query);
    res.json(books);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Reserve
app.post('/books/reserve/:id', async (req, res) => {
  try {
    const { user } = req.body;
    const book = await Book.findById(req.params.id);
    if (!book || book.availableCopies <= 0)
      return res.status(400).json({ message: "Book not available" });
    if (book.reservedBy.includes(user))
      return res.status(400).json({ message: "Already reserved by this user" });
    book.availableCopies--;
    book.reservedBy.push(user);
    await book.save();
    res.json({ message: "Book reserved successfully", book });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Cancel Reserve
app.post('/books/unreserve/:id', async (req, res) => {
  try {
    const { user } = req.body;
    const book = await Book.findById(req.params.id);
    if (!book) return res.status(404).json({ message: "Book not found" });
    if (!book.reservedBy.includes(user))
      return res.status(400).json({ message: "No reservation found for this user" });
    book.availableCopies++;
    book.reservedBy = book.reservedBy.filter(u => u !== user);
    await book.save();
    res.json({ message: "Reservation cancelled", book });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Borrow
app.post('/books/borrow/:id', async (req, res) => {
  try {
    const { user } = req.body;
    const book = await Book.findById(req.params.id);
    if (!book) return res.status(404).json({ message: "Book not found" });

    const hasReservation = book.reservedBy.includes(user);

    // If user reserved it, convert reservation to borrow (no availableCopies change)
    if (hasReservation) {
      book.reservedBy = book.reservedBy.filter(u => u !== user);
    } else {
      if (book.availableCopies <= 0)
        return res.status(400).json({ message: "Book not available" });
      book.availableCopies--;
    }

    book.borrowedBy.push(user);
    await book.save();
    res.json({ message: "Book borrowed successfully", book });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Return
app.post('/books/return/:id', async (req, res) => {
  try {
    const { user } = req.body;
    const book = await Book.findById(req.params.id);
    if (!book) return res.status(404).json({ message: "Book not found" });
    if (!book.borrowedBy.includes(user))
      return res.status(400).json({ message: "No borrow record for this user" });
    book.availableCopies++;
    book.borrowedBy = book.borrowedBy.filter(u => u !== user);
    await book.save();
    res.json({ message: "Book returned successfully", book });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

app.listen(3000, () => console.log("Server running on port 3000"));