import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPessoa } from 'app/shared/model/pessoa.model';
import { getEntities } from './pessoa.reducer';

export const Pessoa = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const pessoaList = useAppSelector(state => state.pessoa.entities);
  const loading = useAppSelector(state => state.pessoa.loading);
  const totalItems = useAppSelector(state => state.pessoa.totalItems);

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
      <h2 id="pessoa-heading" data-cy="PessoaHeading">
        <Translate contentKey="escolaEbdApp.pessoa.home.title">Pessoas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="escolaEbdApp.pessoa.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pessoa/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="escolaEbdApp.pessoa.home.createLabel">Create new Pessoa</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pessoaList && pessoaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="escolaEbdApp.pessoa.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nome')}>
                  <Translate contentKey="escolaEbdApp.pessoa.nome">Nome</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataNascimento')}>
                  <Translate contentKey="escolaEbdApp.pessoa.dataNascimento">Data Nascimento</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cpf')}>
                  <Translate contentKey="escolaEbdApp.pessoa.cpf">Cpf</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sexo')}>
                  <Translate contentKey="escolaEbdApp.pessoa.sexo">Sexo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tipoPessoa')}>
                  <Translate contentKey="escolaEbdApp.pessoa.tipoPessoa">Tipo Pessoa</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataCadastro')}>
                  <Translate contentKey="escolaEbdApp.pessoa.dataCadastro">Data Cadastro</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataAtualizacao')}>
                  <Translate contentKey="escolaEbdApp.pessoa.dataAtualizacao">Data Atualizacao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="escolaEbdApp.pessoa.turma">Turma</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pessoaList.map((pessoa, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pessoa/${pessoa.id}`} color="link" size="sm">
                      {pessoa.id}
                    </Button>
                  </td>
                  <td>{pessoa.nome}</td>
                  <td>
                    {pessoa.dataNascimento ? <TextFormat type="date" value={pessoa.dataNascimento} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{pessoa.cpf}</td>
                  <td>
                    <Translate contentKey={`escolaEbdApp.Sexo.${pessoa.sexo}`} />
                  </td>
                  <td>
                    <Translate contentKey={`escolaEbdApp.TipoPessoa.${pessoa.tipoPessoa}`} />
                  </td>
                  <td>{pessoa.dataCadastro ? <TextFormat type="date" value={pessoa.dataCadastro} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {pessoa.dataAtualizacao ? <TextFormat type="date" value={pessoa.dataAtualizacao} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{pessoa.turma ? <Link to={`/turma/${pessoa.turma.id}`}>{pessoa.turma.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pessoa/${pessoa.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pessoa/${pessoa.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/pessoa/${pessoa.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="escolaEbdApp.pessoa.home.notFound">No Pessoas found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={pessoaList && pessoaList.length > 0 ? '' : 'd-none'}>
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

export default Pessoa;
