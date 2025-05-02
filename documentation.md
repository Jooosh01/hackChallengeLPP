# Language Learning App Backend API Documentation

## Table of Contents
1. [Overview](#overview)
2. [Setup and Installation](#setup-and-installation)
3. [Authentication](#authentication)
4. [Data Models](#data-models)
5. [API Endpoints](#api-endpoints)
   - [Authentication](#authentication-endpoints)
   - [Languages](#languages-endpoints)
   - [Users](#users-endpoints)
   - [Matches](#matches-endpoints)
   - [Chatrooms](#chatrooms-endpoints)
6. [WebSocket Integration](#websocket-integration)
7. [Error Handling](#error-handling)

## Overview

This repository contains the backend API for the Language Learning App, which facilitates language exchange between users. The backend provides functionality for user management, language selection, matching users, and real-time chat communication.

## Setup and Installation

### Prerequisites
- Python 3.8+
- Flask
- Flask-JWT-Extended
- Flask-SocketIO
- SQLAlchemy

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd <repository-directory>
```

2. Create and activate a virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

3. Install dependencies:
```bash
pip install -r requirements.txt
```

4. Set up environment variables:
```bash
export FLASK_APP=app.py
export FLASK_ENV=development
export DATABASE_URL=sqlite:///lang.db  # Or your PostgreSQL URL
export JWT_SECRET_KEY=your_secret_key
```

5. Run the application:
```bash
flask run
# or
python app.py
```

The API will be available at `http://localhost:5000`.

## Authentication

The API uses JWT (JSON Web Token) for authentication. Most endpoints require a valid JWT token in the Authorization header.

### Token Format
```
Authorization: Bearer Token <your_token>
```

## Data Models

### User
```json
{
  "id": 1,
  "netID": "user123",
  "name": "John Doe",
  "level": "intermediate",
  "match_status": false,
  "description": "I want to improve my Spanish",
  "language": {
    "id": 1,
    "name": "Spanish",
    "flag_icon_url": "https://example.com/flags/spain.png"
  }
}
```

### Language
```json
{
  "id": 1,
  "name": "Spanish",
  "flag_icon_url": "https://example.com/flags/spain.png"
}
```

### Match
```json
{
  "id": 1,
  "user1": {
    // User object for the first user
  },
  "user2": {
    // User object for the second user
  },
  "status": "pending",
  "timestamp": "2025-05-01T12:00:00"
}
```

### Chatroom
```json
{
  "id": 1,
  "user1_id": 1,
  "user2_id": 2,
  "timestamp": "2025-05-01T12:00:00",
  "active": true
}
```

### Message
```json
{
  "id": 1,
  "chatroom_id": 1,
  "user_id": 1,
  "content": "Hello, how are you?",
  "timestamp": "2025-05-01T12:01:00"
}
```

## API Endpoints

### Authentication Endpoints

#### Login
- **URL**: `/login/`
- **Method**: `POST`
- **Auth Required**: No
- **Request Body**:
  ```json
  {
    "netID": "user123",
    "password": "password123"
  }
  ```
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "user": {
          // User object
        }
      }
    }
    ```
- **Error Response**:
  - **Code**: 401
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Invalid credentials"
    }
    ```

### Languages Endpoints

#### Get All Languages
- **URL**: `/api/languages/`
- **Method**: `GET`
- **Auth Required**: No
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        "languages": [
          {
            "id": 1,
            "name": "Spanish",
            "flag_icon_url": "https://example.com/flags/spain.png"
          },
          {
            "id": 2,
            "name": "French",
            "flag_icon_url": "https://example.com/flags/france.png"
          }
        ]
      }
    }
    ```

### Users Endpoints

#### Create User (Register)
- **URL**: `/api/users/`
- **Method**: `POST`
- **Auth Required**: No
- **Request Body**:
  ```json
  {
    "netID": "user123",
    "name": "John Doe",
    "password": "password123",
    "level": "intermediate",
    "language_id": 1,
    "description": "I want to improve my Spanish"
  }
  ```
- **Success Response**:
  - **Code**: 201
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // User object
      }
    }
    ```
- **Error Response**:
  - **Code**: 400/500
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Error message"
    }
    ```

#### Get User by ID
- **URL**: `/api/users/<user_id>/`
- **Method**: `GET`
- **Auth Required**: Yes
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // User object
      }
    }
    ```
- **Error Response**:
  - **Code**: 404
  - **Content**:
    ```json
    {
      "success": false,
      "error": "User not found"
    }
    ```

#### Get All Users
- **URL**: `/api/users/`
- **Method**: `GET`
- **Auth Required**: Yes
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        "users": [
          // User objects
        ]
      }
    }
    ```

#### Delete User
- **URL**: `/api/users/<user_id>/`
- **Method**: `DELETE`
- **Auth Required**: Yes
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        "deleted": true
      }
    }
    ```
- **Error Response**:
  - **Code**: 404
  - **Content**:
    ```json
    {
      "success": false,
      "error": "User not found"
    }
    ```

#### Update User Match Status
- **URL**: `/api/users/<user_id>/match_status/`
- **Method**: `PUT`
- **Auth Required**: Yes
- **Request Body**:
  ```json
  {
    "match_status": true
  }
  ```
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // Updated User object
      }
    }
    ```
- **Error Response**:
  - **Code**: 404
  - **Content**:
    ```json
    {
      "success": false,
      "error": "User not found"
    }
    ```

### Matches Endpoints

#### Create Match
- **URL**: `/api/matches/`
- **Method**: `POST`
- **Auth Required**: Yes
- **Request Body**:
  ```json
  {
    "user1_id": 1,
    "user2_id": 2
  }
  ```
- **Success Response**:
  - **Code**: 201
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // Match object
      }
    }
    ```
- **Error Response**:
  - **Code**: 400/500
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Error message"
    }
    ```

#### Auto Match
- **URL**: `/api/matches/auto_match/`
- **Method**: `POST`
- **Auth Required**: No
- **Request Body**:
  ```json
  {
    "user_id": 1
  }
  ```
- **Success Response**:
  - **Code**: 201
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // Match object
      }
    }
    ```
- **Error Response**:
  - **Code**: 400
  - **Content**:
    ```json
    {
      "success": false,
      "error": "No suitable match found"
    }
    ```

#### Accept Match
- **URL**: `/api/matches/<match_id>/accept/`
- **Method**: `POST`
- **Auth Required**: Yes
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // Updated Match object with status = "accepted"
      }
    }
    ```
- **Error Response**:
  - **Code**: 404
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Match not found"
    }
    ```

### Chatrooms Endpoints

#### Create Chatroom
- **URL**: `/api/chatroom/`
- **Method**: `POST`
- **Auth Required**: Yes
- **Request Body**:
  ```json
  {
    "user1_id": 1,
    "user2_id": 2
  }
  ```
- **Success Response**:
  - **Code**: 201
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // Chatroom object
      }
    }
    ```
- **Error Response**:
  - **Code**: 400/500
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Error message"
    }
    ```

#### Close Chatroom
- **URL**: `/api/chatroom/<chatroom_id>/`
- **Method**: `PUT`
- **Auth Required**: Yes
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // Updated Chatroom object with active = false
      }
    }
    ```
- **Error Response**:
  - **Code**: 404
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Chatroom not found"
    }
    ```

#### Send Message (HTTP)
- **URL**: `/api/chatroom/<chatroom_id>/messages/`
- **Method**: `POST`
- **Auth Required**: Yes
- **Request Body**:
  ```json
  {
    "user_id": 1,
    "content": "Hello, how are you?"
  }
  ```
- **Success Response**:
  - **Code**: 201
  - **Content**:
    ```json
    {
      "success": true,
      "data": {
        // Message object
      }
    }
    ```
- **Error Response**:
  - **Code**: 400/500
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Error message"
    }
    ```

#### Get Message History
- **URL**: `/api/chatroom/<chatroom_id>/messages/`
- **Method**: `GET`
- **Auth Required**: Yes
- **Success Response**:
  - **Code**: 200
  - **Content**:
    ```json
    {
      "success": true,
      "data": [
        // Array of Message objects
      ]
    }
    ```
- **Error Response**:
  - **Code**: 404
  - **Content**:
    ```json
    {
      "success": false,
      "error": "Chatroom not found"
    }
    ```

## WebSocket Integration

The API includes real-time messaging capabilities using Socket.IO.

### Connection
Connect to the WebSocket server at the same host and port as the REST API. For example, if your backend is running at `http://localhost:5000`, connect to `http://localhost:5000` for Socket.IO.

### Events

#### Join Chatroom
- **Event**: `join chatroom`
- **Payload**:
  ```json
  {
    "chatroom_id": 1,
    "user_id": 1
  }
  ```
- **Response Events**:
  - **Success**: `user_joined`
    ```json
    {
      "msg": "User joined chatroom 1"
    }
    ```
  - **Error**: `error`
    ```json
    {
      "error": "Error message"
    }
    ```

#### Send Message
- **Event**: `send message`
- **Payload**:
  ```json
  {
    "chatroom_id": 1,
    "user_id": 1,
    "content": "Hello, how are you?"
  }
  ```
- **Response Events**:
  - **Success**: `receive_message` (broadcast to all users in the chatroom)
    ```json
    {
      // Message object
    }
    ```
  - **Error**: `error`
    ```json
    {
      "error": "Error message"
    }
    ```

## Error Handling

All API endpoints return a consistent error format:

```json
{
  "success": false,
  "error": "Error message"
}
```

Common HTTP status codes:
- `200` - Success
- `201` - Resource created
- `400` - Bad request (invalid parameters)
- `401` - Unauthorized (missing or invalid token)
- `404` - Resource not found
- `500` - Server error

## Frontend Integration Examples

### Authentication Example (JavaScript)

```javascript
// Login example
async function login(netID, password) {
  try {
    const response = await fetch('/login/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        netID: netID,
        password: password
      })
    });
    
    const data = await response.json();
    
    if (data.success) {
      // Store token in localStorage
      localStorage.setItem('token', data.data.token);
      return data.data.user;
    } else {
      throw new Error(data.error);
    }
  } catch (error) {
    console.error('Login failed:', error);
    throw error;
  }
}
```

### WebSocket Chat Example (JavaScript with Socket.IO)

```javascript
// Connect to Socket.IO server
const socket = io();

// Join a chatroom
function joinChatroom(chatroomId, userId) {
  socket.emit('join chatroom', {
    chatroom_id: chatroomId,
    user_id: userId
  });
}

// Listen for new messages
socket.on('receive_message', (message) => {
  // Add message to UI
  displayMessage(message);
});

// Listen for user join event
socket.on('user_joined', (data) => {
  console.log(data.msg);
  // Update UI to show user joined
});

// Listen for errors
socket.on('error', (data) => {
  console.error('Socket error:', data.error);
  // Display error to user
});

// Send a message
function sendMessage(chatroomId, userId, content) {
  socket.emit('send message', {
    chatroom_id: chatroomId,
    user_id: userId,
    content: content
  });
}
```

### Making Authenticated Requests (JavaScript)

```javascript
// Helper function for authenticated API calls
async function authenticatedFetch(url, options = {}) {
  const token = localStorage.getItem('token');
  
  if (!token) {
    throw new Error('Not authenticated');
  }
  
  const headers = {
    ...options.headers,
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };
  
  const response = await fetch(url, {
    ...options,
    headers
  });
  
  const data = await response.json();
  
  if (!data.success) {
    throw new Error(data.error);
  }
  
  return data.data;
}

// Example: Get user profile
async function getUserProfile(userId) {
  return await authenticatedFetch(`/api/users/${userId}/`);
}

// Example: Get all available users for matching
async function getAllUsers() {
  return await authenticatedFetch('/api/users/');
}
```