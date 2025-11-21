export default function TaskList({ tasks, taskStates, loading, onEdit, onDelete }) {
  const getStateName = (stateId) => {
    const state = taskStates.find(s => s.id === stateId);
    return state ? state.name : 'Desconocido';
  };

  const getStateClass = (stateName) => {
    const stateMap = {
      'TODO': 'state-todo',
      'IN_PROGRESS': 'state-in-progress',
      'IN_REVIEW': 'state-in-review',
      'DONE': 'state-done',
      'BLOCKED': 'state-blocked'
    };
    return stateMap[stateName] || 'state-default';
  };

  const formatDate = (dateString) => {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  if (loading) {
    return <div className="loading">Cargando tareas...</div>;
  }

  if (!Array.isArray(tasks) || tasks.length === 0) {
    return (
      <div className="empty-state">
        <p>No hay tareas disponibles</p>
        <p className="empty-state-hint">Crea una nueva tarea para comenzar</p>
      </div>
    );
  }

  return (
    <div className="task-list">
      <table className="task-table">
        <thead>
          <tr>
            <th>Título</th>
            <th>Descripción</th>
            <th>Estado</th>
            <th>Asignado a</th>
            <th>Creada por</th>
            <th>Fecha creación</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map((task) => {
            const stateName = getStateName(task.state?.id);
            return (
              <tr key={task.id}>
                <td className="task-title">{task.title}</td>
                <td className="task-description">
                  {task.description || <em className="text-muted">Sin descripción</em>}
                </td>
                <td>
                  <span className={`task-state ${getStateClass(stateName)}`}>
                    {task.state?.name || 'N/A'}
                  </span>
                </td>
                <td>{task.assignedUser?.fullName || task.assignedUser?.username || '-'}</td>
                <td>{task.createdBy?.fullName || task.createdBy?.username || '-'}</td>
                <td>{formatDate(task.createdAt)}</td>
                <td className="task-actions">
                  <button
                    onClick={() => onEdit(task)}
                    className="btn-icon btn-edit"
                    title="Editar"
                  >
                    ✏️
                  </button>
                  <button
                    onClick={() => onDelete(task.id)}
                    className="btn-icon btn-delete"
                    title="Eliminar"
                  >
                    🗑️
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
