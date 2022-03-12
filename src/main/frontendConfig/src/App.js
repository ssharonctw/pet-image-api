import logo from './logo.svg';
import './App.css';
import axios from "axios";
import React,{useState, useEffect, useCallback} from 'react';
import {useDropzone} from 'react-dropzone'

//a functional component
const UserProfiles = () =>{

const[userProfiles, setUserProfiles] = useState([])

  const fetchUserProfiles=()=>{
    axios.get("http://localhost:8080/api/v1/user-profile").then(res=>{
      console.log(res);
      const cleanedData = res.data; //to only keep the data part of the response (the original response have property, header ... which we don't need)
      setUserProfiles(cleanedData);
    });
  };

  useEffect(()=>{
    fetchUserProfiles();
  },[] );

  return userProfiles.map((userProfile, index)=>{
    return(
    <div key={index}>
      {userProfile.userProfileId? (
        <img 
          src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`} 
        />) : null}
      {/**todo: profile image */}
      <br/>
      <br/>
      <h1>{userProfile.username}</h1>
      {/**<p>{userProfile.userProfileId}</p>*/}
      <MyDropzone {...userProfile}/>
      <br/>
    </div>
    )
  })
};

function MyDropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    // Do something with the files
    const file = acceptedFiles[0];
    console.log(file)
    //append the file to formData to be send to backend
    const formData = new FormData();
    formData.append("file", file);

    axios.post(
      `http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers:{
          "Content-Type":"multipart/form-data"
        }
      }
    ).then(()=>{
      console.log("file uploaded successfully");
    }).catch(err=>{
      console.log(err);
    });
    
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the images here ...</p> :
          <p>Drop pet image here, or click to select pet images</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}

export default App;
