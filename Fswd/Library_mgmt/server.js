const express = require('express'); 
const mongoose = require('mongoose'); 
const bodyParser = require('body-parser'); 
const Book = require('./models/Book'); 
 
const app = express(); 
app.use(bodyParser.json()); 
 
// Connect to MongoDB 
mongoose.connect('mongodb://127.0.0.1:27017/libraryDB') 
.then(() => console.log("MongoDB Connected")) 
.catch(err => console.log(err)); 
 
/* ================= ADMIN ROUTES ================= */ 
 
// Add new book 
app.post('/admin/add-book', async (req, res) => { 
    try { 
        const { title, author, category, totalCopies } = req.body; 
 
        const book = new Book({ 
            title, 
            author, 
            category, 
            totalCopies, 
            availableCopies: totalCopies 
        }); 
 
        await book.save(); 
        res.json({ message: "Book added successfully", book }); 
    } catch (err) { 
        res.status(500).json({ error: err.message }); 
    } 
}); 
 
// View inventory 
app.get('/admin/inventory', async (req, res) => { 
    const books = await Book.find(); 
    res.json(books); 
}); 
 
/* ================= USER ROUTES ================= */ 
 
// Get all books 
app.get('/books', async (req, res) => { 
    const books = await Book.find(); 
    res.json(books); 
}); 
 
// Search book 
app.get('/books/search', async (req, res) => { 
    const { title, category } = req.query; 
 
    const query = {}; 
    if (title) query.title = new RegExp(title, 'i'); 
    if (category) query.category = category; 
 
    const books = await Book.find(query); 
    res.json(books); 
}); 
 
// Reserve book 
app.post('/books/reserve/:id', async (req, res) => { 
    const { user } = req.body; 
    const book = await Book.findById(req.params.id); 
 
    if (!book || book.availableCopies <= 0) 
        return res.status(400).json({ message: "Book not available" }); 
 
    book.availableCopies--; 
    book.reservedBy.push(user); 
    await book.save(); 
 
    res.json({ message: "Book reserved successfully", book }); 
}); 
 
// Borrow book 
app.post('/books/borrow/:id', async (req, res) => { 
    const { user } = req.body; 
    const book = await Book.findById(req.params.id); 
 
    if (!book || book.availableCopies <= 0) 
        return res.status(400).json({ message: "Book not available" }); 
 
    book.availableCopies--; 
    book.borrowedBy.push(user); 
    await book.save(); 
 
    res.json({ message: "Book borrowed successfully", book }); 
}); 
 
// Return book 
app.post('/books/return/:id', async (req, res) => { 
    const { user } = req.body; 
    const book = await Book.findById(req.params.id); 
 
    if (!book) 
        return res.status(404).json({ message: "Book not found" }); 
 
    book.availableCopies++; 
    book.borrowedBy = book.borrowedBy.filter(u => u !== user); 
    await book.save(); 
 
    res.json({ message: "Book returned successfully", book }); 
}); 
 
/* ================= SERVER ================= */ 
 
app.listen(3000, () => { 
    console.log("Server running on port 3000"); 
}); 
