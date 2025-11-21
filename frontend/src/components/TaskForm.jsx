import { useState, useEffect } from 'react';

export default function TaskForm({ task, taskStates, onSave, onCancel }) {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    stateId: '',
    assignedUserId: null
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (task) {
      setFormData({
        title: task.title || '',
        description: task.description || '',
        stateId: task.state?.id || '',
        assignedUserId: task.assignedUser?.id || null
      });
    }
  }, [task]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await onSave(formData);
    } catch (err) {
      setError(err.message || 'Error al guardar tarea');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value || (name === 'stateId' ? '' : null) });
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h3>{task ? 'Editar Tarea' : 'Nueva Tarea'}</h3>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="title">Título *</label>
            <input
              type="text"
              id="title"
              name="title"
              value={formData.title}
              onChange={handleChange}
              required
              minLength="3"
              maxLength="200"
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label htmlFor="description">Descripción</label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              rows="4"
              maxLength="2000"
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label htmlFor="stateId">Estado *</label>
            <select
              id="stateId"
              name="stateId"
              value={formData.stateId}
              onChange={handleChange}
              required
              disabled={loading}
            >
              <option value="">Seleccionar estado</option>
              {taskStates.map((state) => (
                <option key={state.id} value={state.id}>
                  {state.name}
                </option>
              ))}
            </select>
          </div>

          {error && <div className="error-message">{error}</div>}

          <div className="form-actions">
            <button type="button" onClick={onCancel} className="btn-secondary" disabled={loading}>
              Cancelar
            </button>
            <button type="submit" className="btn-primary" disabled={loading}>
              {loading ? 'Guardando...' : 'Guardar'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
