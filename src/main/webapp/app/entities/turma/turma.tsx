import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITurma } from 'app/shared/model/turma.model';
import { getEntities } from './turma.reducer';

export const Turma = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const turmaList = useAppSelector(state => state.turma.entities);
  const loading = useAppSelector(state => state.turma.loading);
  const totalItems = useAppSelector(state => state.turma.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="turma-heading" data-cy="TurmaHeading">
        <Translate contentKey="escolaEbdApp.turma.home.title">Turmas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="escolaEbdApp.turma.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/turma/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="escolaEbdApp.turma.home.createLabel">Create new Turma</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {turmaList && turmaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="escolaEbdApp.turma.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nome')}>
                  <Translate contentKey="escolaEbdApp.turma.nome">Nome</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sexoTurma')}>
                  <Translate contentKey="escolaEbdApp.turma.sexoTurma">Sexo Turma</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('faixaEtaria')}>
                  <Translate contentKey="escolaEbdApp.turma.faixaEtaria">Faixa Etaria</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataInicio')}>
                  <Translate contentKey="escolaEbdApp.turma.dataInicio">Data Inicio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataFim')}>
                  <Translate contentKey="escolaEbdApp.turma.dataFim">Data Fim</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataCadastro')}>
                  <Translate contentKey="escolaEbdApp.turma.dataCadastro">Data Cadastro</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="escolaEbdApp.turma.professor">Professor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {turmaList.map((turma, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/turma/${turma.id}`} color="link" size="sm">
                      {turma.id}
                    </Button>
                  </td>
                  <td>{turma.nome}</td>
                  <td>
                    <Translate contentKey={`escolaEbdApp.SexoTurma.${turma.sexoTurma}`} />
                  </td>
                  <td>
                    <Translate contentKey={`escolaEbdApp.FaixaEtaria.${turma.faixaEtaria}`} />
                  </td>
                  <td>{turma.dataInicio ? <TextFormat type="date" value={turma.dataInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{turma.dataFim ? <TextFormat type="date" value={turma.dataFim} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{turma.dataCadastro ? <TextFormat type="date" value={turma.dataCadastro} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{turma.professor ? <Link to={`/pessoa/${turma.professor.id}`}>{turma.professor.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/turma/${turma.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/turma/${turma.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/turma/${turma.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="escolaEbdApp.turma.home.notFound">No Turmas found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={turmaList && turmaList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Turma;
