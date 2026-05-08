function AnalysisCard({
  analysis,
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
  const isInterpreting = interpretingAnalysisId === analysis.id;
  const formatAiText = (text) => {
    if (!text) return "";

    return text
      .replaceAll("**", "")
      .replaceAll("###", "")
      .replaceAll("##", "")
      .replaceAll("#", "")
      .trim();
  };



  return (
    <div className="analysis-card">
      {editingAnalysisId === analysis.id ? (
        <div>
          <h3>Edit Analysis</h3>

          <input
            name="analysisType"
            placeholder="Analysis type"
            value={editForm.analysisType}
            onChange={handleEditChange}
          />

          <input
            name="value"
            type="number"
            step="0.01"
            placeholder="Value"
            value={editForm.value}
            onChange={handleEditChange}
          />

          <input
            name="unit"
            placeholder="Unit"
            value={editForm.unit}
            onChange={handleEditChange}
          />

          <input
            name="collectionDate"
            type="date"
            value={editForm.collectionDate}
            onChange={handleEditChange}
          />

          <button onClick={() => handleUpdateAnalysis(analysis.id)}>
            Save
          </button>

          <button onClick={handleCancelEdit}>
            Cancel
          </button>
        </div>
      ) : (
        <div>
          <h3>{analysis.analysisType}</h3>

          <p>
            Value: {analysis.value} {analysis.unit}
          </p>

          <p>Collected on: {analysis.collectionDate}</p>

          {analysis.aiInterpretation && (
            <div className="ai-box">
              <strong>AI Interpretation</strong>

              {formatAiText(analysis.aiInterpretation)
                .split("\n")
                .filter((line) => line.trim() !== "")
                .map((line, index) => (
                  <p key={index}>{line}</p>
                ))}
            </div>
          )}

          {analysis.fileName && (
            <div className="attached-file">
              <p>📎 {analysis.fileName}</p>

              <button
                onClick={() =>
                  handleDownloadFile(analysis.id, analysis.fileName)
                }
              >
                Download file
              </button>
            </div>
          )}

          <div className="analysis-actions">
            <button
              onClick={() => handleInterpretAnalysis(analysis.id)}
              disabled={isInterpreting}
              className="ai-button"
            >
              {isInterpreting ? (
                <>
                  <span className="spinner"></span>
                  Generating...
                </>
              ) : (
                "Generate AI interpretation"
              )}
            </button>

            <button onClick={() => handleEditClick(analysis)}>Edit</button>

            <button onClick={() => handleDeleteAnalysis(analysis.id)}>
              Delete
            </button>
          </div>
        </div>
      )}

      <div className="file-upload">
        <input
          type="file"
          onChange={(event) => handleFileChange(analysis.id, event)}
        />

        <button onClick={() => handleUploadFile(analysis.id)}>
          Upload File
        </button>
      </div>
    </div>
  );
}

export default AnalysisCard;