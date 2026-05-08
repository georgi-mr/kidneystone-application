function AnalysisForm({ analysisForm, handleAnalysisChange, handleCreateAnalysis }) {
  return (
    <form className="add-analysis-section" onSubmit={handleCreateAnalysis}>
      <h2>Add Analysis</h2>

      <input
        name="analysisType"
        placeholder="Analysis type"
        value={analysisForm.analysisType}
        onChange={handleAnalysisChange}
      />

      <input
        name="value"
        type="number"
        step="0.01"
        placeholder="Value"
        value={analysisForm.value}
        onChange={handleAnalysisChange}
      />

      <input
        name="unit"
        placeholder="Unit"
        value={analysisForm.unit}
        onChange={handleAnalysisChange}
      />

      <input
        name="collectionDate"
        type="date"
        value={analysisForm.collectionDate}
        onChange={handleAnalysisChange}
      />

      <button type="submit">Add analysis</button>
    </form>
  );
}

export default AnalysisForm;