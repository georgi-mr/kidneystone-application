const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";


export async function registerUser(userData) {
    const response = await fetch(`${API_BASE_URL}/api/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userData)
    });

    const data = await response.json();
    if (!response.ok) {
        throw new Error(data.message || "Register failed");
    }
    return data;
}

export async function loginUser(credentials) {
    const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(credentials),
    });

    const data = await response.json();

    if (!response.ok) {
        throw new Error(data.message || "Login failed");
    }

    return data;
}

export async function getAnalyses() {
    const token = localStorage.getItem("token");

    const response = await fetch(`${API_BASE_URL}/api/analyses`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        },
    });

    const data = await response.json();
    if (!response.ok) {
        throw new Error(data.message || "Failed to fetch analyses");
    }
    return data;

}

export async function createAnalysis(analysisdata) {
    const token = localStorage.getItem("token");

    const response = await fetch(`${API_BASE_URL}/api/analyses`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(analysisdata)
    });

    const data = await response.json();
    if (!response.ok) {
        throw new Error(data.message || "Failed to create analysis");
    }
    return data;
}

export async function deleteAnalysis(id) {
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_BASE_URL}/api/analyses/${id}`,
        {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }

    );
    if (!response.ok) {
        let errorMessage = "Failed to delete analysis";
        try {
            const data = await response.json();
            errorMessage = data.message || errorMessage;
        } catch {
            // Ignore JSON parsing errors and use default message
        }

        throw new Error(errorMessage);
    }

}

export async function updateAnalysis(id,analysisData){
    const token = localStorage.getItem("token");
    const response= await fetch(`${API_BASE_URL}/api/analyses/${id}`,{
        method:"PUT",
        headers:{
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body:JSON.stringify(analysisData),
    
    });

    const data= await response.json();
    if(!response.ok){
        throw new Error(data.message || "Failed to update analysis");
    }

    return data;
}

export async function interpretAnalysis(id){
    const token=localStorage.getItem("token");
    const response=await fetch(`${API_BASE_URL}/api/analyses/${id}/interpret`,{
        method:"POST",
        headers:{
            "Authorization": `Bearer ${token}`
        }
    });

    const data= await response.json();
    if(!response.ok){
        throw new Error(data.message || "Failed to interpret analysis");
    }

    return data;
}

export async function uploadFile(id,file) {
    const token = localStorage.getItem("token");
    const formData=new FormData();
    formData.append("file",file);
    const response=await fetch(`${API_BASE_URL}/api/analyses/${id}/file`,{
        method:"POST",
        headers:{
            "Authorization": `Bearer ${token}`
        },
        body:formData
    });
    const data= await response.json();
    if(!response.ok){
        throw new Error(data.message || "Failed to upload file");
    }
    return data;
}
export async function downloadAnalysisFile(id, fileName) {
  const token = localStorage.getItem("token");

  const response = await fetch(`${API_BASE_URL}/api/analyses/${id}/file/download`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    let errorMessage = "Failed to download file";

    try {
      const data = await response.json();
      errorMessage = data.message || errorMessage;
    } catch {
      // response may not be JSON
    }

    throw new Error(errorMessage);
  }

  const blob = await response.blob();
  const url = window.URL.createObjectURL(blob);

  const link = document.createElement("a");
  link.href = url;
  link.download = fileName || "analysis-file";
  document.body.appendChild(link);
  link.click();

  link.remove();
  window.URL.revokeObjectURL(url);
}