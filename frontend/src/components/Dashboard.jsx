import AnalysisForm from "./AnalysisForm";
import AnalysisList from "./AnalysisList";

function Dashboard({
  handleLogout,
  loadAnalyses,
  analyses,
  searchTerm,
  setSearchTerm,
  showAddForm,
  setShowAddForm,
  analysisForm,
  handleAnalysisChange,
  handleCreateAnalysis,
  editingAnalysisId,
  editForm,
  handleEditClick,
  handleEditChange,
  handleCancelEdit,
  handleUpdateAnalysis,
  handleDeleteAnalysis,
  handleInterpretAnalysis,
  handleFileChange,
  handleUploadFile,
  handleDownloadFile,
  interpretingAnalysisId,
}) {
  return (
    <div>
      <div className="dashboard-header">
        <h2>You are logged in</h2>

        <div>
          <button onClick={handleLogout}>Logout</button>
          <button onClick={loadAnalyses}>Refresh</button>
        </div>
      </div>

      <div className="analyses-toolbar">
        <h2>My Analyses</h2>

        <div className="analyses-toolbar-actions">
          <input
            className="search-input"
            type="text"
            placeholder="Search by type, value, unit, date or file..."
            value={searchTerm}
            onChange={(event) => setSearchTerm(event.target.value)}
          />

          <button onClick={() => setShowAddForm(!showAddForm)}>
            {showAddForm ? "Close" : "Add Analysis"}
          </button>
        </div>
      </div>

      {showAddForm && (
        <AnalysisForm
          analysisForm={analysisForm}
          handleAnalysisChange={handleAnalysisChange}
          handleCreateAnalysis={handleCreateAnalysis}
        />
      )}

      <AnalysisList
        analyses={analyses}
        searchTerm={searchTerm}
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
      />
    </div>
  );
}

export default Dashboard;