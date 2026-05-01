import React, { useState } from "react"; 
import "./App.css"; 
 
function App() { 
  const [campaigns, setCampaigns] = useState([]); 
  const [title, setTitle] = useState(""); 
  const [description, setDescription] = useState(""); 
  const [target, setTarget] = useState(""); 
 
  const createCampaign = () => { 
    if (!title || !target) return alert("Please fill required fields"); 
 
    const newCampaign = { 
      id: Date.now(), 
      title, 
      description, 
      target: parseFloat(target), 
      raised: 0, 
      donors: [] 
    }; 
 
    setCampaigns([...campaigns, newCampaign]); 
    setTitle(""); 
    setDescription(""); 
    setTarget(""); 
  }; 
 
  const donate = (id, donorName, amount) => { 
    if (!donorName || !amount) return alert("Enter valid details"); 
 
    setCampaigns( 
      campaigns.map((campaign) => 
        campaign.id === id 
          ? { 
              ...campaign, 
              raised: campaign.raised + parseFloat(amount), 
              donors: [...campaign.donors, { name: donorName, amount }] 
            } 
          : campaign 
      ) 
    ); 
  }; 
 
  return ( 
    <div className="container"> 
      <h1>Donation Platform</h1> 
 
      {/* Create Campaign */} 
      <div className="card"> 
        <h2>Create Campaign</h2> 
        <input 
          type="text" 
          placeholder="Campaign Title" 
          value={title} 
          onChange={(e) => setTitle(e.target.value)} 
        /> 
        <textarea 
          placeholder="Description" 
          value={description} 
          onChange={(e) => setDescription(e.target.value)} 
        /> 
        <input 
          type="number" 
          placeholder="Target Amount" 
          value={target} 
          onChange={(e) => setTarget(e.target.value)} 
        /> 
        <button onClick={createCampaign}>Create</button> 
      </div> 
 
      {/* Campaign List */} 
      {campaigns.map((campaign) => { 
        const progress = Math.min( 
          (campaign.raised / campaign.target) * 100, 
          100 
        ).toFixed(0); 
 
        let donorName = ""; 
        let amount = ""; 
 
        return ( 
          <div className="card" key={campaign.id}> 
            <h3>{campaign.title}</h3> 
            <p>{campaign.description}</p> 
            <p> 
              ₹{campaign.raised} raised of ₹{campaign.target} 
            </p> 
 
            {/* Progress Bar */} 
            <div className="progress-bar"> 
              <div 
                className="progress" 
                style={{ width: progress + "%" }} 
              > 
                {progress}% 
              </div> 
            </div> 
 
            {/* Donation Form */} 
            <input 
              type="text" 
              placeholder="Your Name" 
              onChange={(e) => (donorName = e.target.value)} 
            /> 
            <input 
              type="number" 
              placeholder="Donation Amount" 
              onChange={(e) => (amount = e.target.value)} 
            /> 
            <button 
              onClick={() => donate(campaign.id, donorName, amount)} 
            > 
              Donate 
            </button> 
 
            {/* Donor List */} 
            <h4>Donors:</h4> 
            <ul> 
              {campaign.donors.map((donor, index) => ( 
                <li key={index}> 
                  {donor.name} donated ₹{donor.amount} 
                </li> 
              ))} 
            </ul> 
          </div> 
        ); 
      })} 
    </div> 
  ); 
} 
 
export default App; 