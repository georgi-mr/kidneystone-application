import { useState, useEffect } from "react";
import {
  loginUser,
  registerUser,
  getAnalyses,
  createAnalysis,
  deleteAnalysis,
  updateAnalysis,
  interpretAnalysis,
  uploadFile,
  downloadAnalysisFile,
} from "./services/api";
import "./App.css";
import AuthForms from "./components/AuthForms";
import Dashboard from "./components/Dashboard";


function App() {
  const [registerForm, setRegisterForm] = useState(
    {
      "username": "",
      "email": "",
      "password": ""
    }
  );

  const [loginForm, setLoginForm] = useState({
    "email": "",
    "password": ""
  });



  const [message, setMessage] = useState("");
  const [token, setToken] = useState(localStorage.getItem("token") || "");
  const [analyses, setAnalyses] = useState([]);
  const [selectedFile, setSelectedFile] = useState({});
  const [authMode, setAuthMode] = useState("login");
  const [interpretingAnalysisId, setInterpretingAnalysisId] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [editingAnalysisId, setEditingAnalysisId] = useState(null);
  const [showAddForm, setShowAddForm] = useState(false);


  const [analysisForm, setAnalysisForm] = useState({
    analysisType: "",
    value: "",
    unit: "",
    collectionDate: "",
  });
  const [editForm, setEditForm] = useState({
    analysisType: "",
    value: "",
    unit: "",
    collectionDate: "",
  });

  useEffect(() => {
    if (token) {
      loadAnalyses();
    }
  }, [token]);

  const handleRegisterChange = (e) => {
    setRegisterForm({
      ...registerForm,
      [e.target.name]: e.target.value
    });
  };

  const handleLoginChange = (e) => {
    setLoginForm({
      ...loginForm,
      [e.target.name]: e.target.value
    });
  }
  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      await registerUser(registerForm);

      setRegisterForm({
        username: "",
        email: "",
        password: "",
      });

      setAuthMode("login");
      setMessage("Registration successful! Please log in.");
    } catch (error) {
      setMessage(error.message);
    }
  };
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const data = await loginUser(loginForm);
      localStorage.setItem("token", data.token);
      setToken(data.token);
      setMessage("Login successful!");
    } catch (error) {
      setMessage(error.message);
    }
  }

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken("");

    setRegisterForm({
      username: "",
      email: "",
      password: "",
    });

    setLoginForm({
      email: "",
      password: "",
    });

    setMessage("Logged out successfully.");
  };

  const handleAnalysisChange = (event) => {
    setAnalysisForm({
      ...analysisForm,
      [event.target.name]: event.target.value,
    });
  };

  const loadAnalyses = async () => {
    try {
      const data = await getAnalyses();
      setAnalyses(data);
      setMessage("Analyses loaded successfully!");
    } catch (error) {
      setMessage(error.message);
    }
  }

  const handleCreateAnalysis = async (event) => {
    event.preventDefault();
    try {
      const analysisData = {
        analysisType: analysisForm.analysisType,
        value: Number(analysisForm.value),
        unit: analysisForm.unit,
        collectionDate: analysisForm.collectionDate,
      };

      await createAnalysis(analysisData);

      setAnalysisForm({
        analysisType: "",
        value: "",
        unit: "",
        collectionDate: "",
      });

      setShowAddForm(false);
      setMessage("Analysis created successfully.");

      const updatedAnalyses = await getAnalyses();
      setAnalyses(updatedAnalyses);
    } catch (error) {
      setMessage(error.message);
    }
  };

  const handleDeleteAnalysis = async (id) => {
    try {
      await deleteAnalysis(id);
      setMessage("Analysis deleted successfully.");

      const updatedAnalyses = await getAnalyses();
      setAnalyses(updatedAnalyses);

    } catch (error) {
      setMessage(error.message);
    }
  };

  const handleEditClick = (analysis) => {
    setEditingAnalysisId(analysis.id);
    setEditForm({
      analysisType: analysis.analysisType,
      value: analysis.value,
      unit: analysis.unit,
      collectionDate: analysis.collectionDate,
    })

  }
  const handleEditChange = (event) => {
    setEditForm({
      ...editForm,
      [event.target.name]: event.target.value
    });
  }


  const handleCancelEdit = () => {
    setEditingAnalysisId(null);
    setEditForm({
      analysisType: "",
      value: "",
      unit: "",
      collectionDate: "",
    });

  }

  const handleUpdateAnalysis = async (id) => {
    try {
      const updateData = {
        analysisType: editForm.analysisType,
        value: Number(editForm.value),
        unit: editForm.unit,
        collectionDate: editForm.collectionDate,
      }

      await updateAnalysis(id, updateData);
      setMessage("Analysis updated successfully.");

      setEditingAnalysisId(null);
      const updatedAnalyses = await getAnalyses();
      setAnalyses(updatedAnalyses);

    } catch (error) {
      setMessage(error.message);
    }

  }


  const handleFileChange = (analysisId, event) => {
    const file = event.target.files[0];

    setSelectedFile({
      ...selectedFile,
      [analysisId]: file,
    });
  };

  const handleUploadFile = async (analysisId) => {
    const file = selectedFile[analysisId];
    if (!file) {
      setMessage("Please select a file to upload.");
      return;
    }
    try {
      await uploadFile(analysisId, file);
      setMessage("File uploaded successfully.");

      const updatedAnalyses = await getAnalyses();
      setAnalyses(updatedAnalyses);
      setSelectedFile({
        ...selectedFile,
        [analysisId]: null
      });
    } catch (error) {
      setMessage(error.message);
    }
  };

  const handleDownloadFile = async (analysisId, fileName) => {
    try {
      await downloadAnalysisFile(analysisId, fileName);
      setMessage("File downloaded successfully.");
    } catch (error) {
      setMessage(error.message);
    }
  };

  const handleInterpretAnalysis = async (id) => {
    try {
      setInterpretingAnalysisId(id);
      setMessage("Interpreting analysis with AI...");

      await interpretAnalysis(id);

      setMessage("Analysis interpreted successfully.");

      const updatedAnalyses = await getAnalyses();
      setAnalyses(updatedAnalyses);
    } catch (error) {
      setMessage(error.message);
    } finally {
      setInterpretingAnalysisId(null);
    }
  };

  return (
    <div className="app">
      <h1>KidneyStone Manager</h1>

      {message && <p>{message}</p>}

      {token ? (
        <Dashboard
          handleLogout={handleLogout}
          loadAnalyses={loadAnalyses}
          analyses={analyses}
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
          analysisForm={analysisForm}
          handleAnalysisChange={handleAnalysisChange}
          handleCreateAnalysis={handleCreateAnalysis}
          editingAnalysisId={editingAnalysisId}
          editForm={editForm}
          handleEditClick={handleEditClick}
          handleEditChange={handleEditChange}
          handleCancelEdit={handleCancelEdit}
          handleUpdateAnalysis={handleUpdateAnalysis}
          handleDeleteAnalysis={handleDeleteAnalysis}
          handleInterpretAnalysis={handleInterpretAnalysis}
          handleFileChange={handleFileChange}
          handleUploadFile={handleUploadFile}
          handleDownloadFile={handleDownloadFile}
          interpretingAnalysisId={interpretingAnalysisId}
          showAddForm={showAddForm}
          setShowAddForm={setShowAddForm}
        />
      ) : (
        <AuthForms
          authMode={authMode}
          setAuthMode={setAuthMode}
          registerForm={registerForm}
          loginForm={loginForm}
          handleRegisterChange={handleRegisterChange}
          handleLoginChange={handleLoginChange}
          handleRegister={handleRegister}
          handleLogin={handleLogin}
        />
      )}
    </div>
  );
}

export default App;
