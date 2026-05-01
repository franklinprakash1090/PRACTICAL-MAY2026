const mongoose = require('mongoose'); 
const bookSchema = new mongoose.Schema({ 
    title: { type: String, required: true }, 
    author: { type: String, required: true }, 
    category: String, 
    totalCopies: { type: Number, required: true }, 
    availableCopies: { type: Number, required: true }, 
    reservedBy: [{ type: String }], 
    borrowedBy: [{ type: String }] 
}); 
module.exports = mongoose.model('Book', bookSchema);
