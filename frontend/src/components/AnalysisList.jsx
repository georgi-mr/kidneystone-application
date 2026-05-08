import AnalysisCard from "./AnalysisCard";

function AnalysisList({
  analyses,
  searchTerm,
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
  const filteredAnalyses = analyses.filter((analysis) => {
    const search = searchTerm.toLowerCase();

    return (
      analysis.analysisType?.toLowerCase().includes(search) ||
      analysis.unit?.toLowerCase().includes(search) ||
      analysis.collectionDate?.toLowerCase().includes(search) ||
      analysis.fileName?.toLowerCase().includes(search) ||
      String(analysis.value).includes(search)
    );
  });

  return (
    <div className="my-analyses">
      {analyses.length === 0 ? (
        <p>No analyses found. Please add an analysis.</p>
      ) : filteredAnalyses.length === 0 ? (
        <p>No analyses match your search.</p>
      ) : (
        filteredAnalyses.map((analysis) => (
          <AnalysisCard
            key={analysis.id}
            analysis={analysis}
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
        ))
      )}
    </div>
  );
}

export default AnalysisList;