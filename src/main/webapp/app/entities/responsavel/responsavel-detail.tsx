import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './responsavel.reducer';

export const ResponsavelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const responsavelEntity = useAppSelector(state => state.responsavel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="responsavelDetailsHeading">
          <Translate contentKey="escolaEbdApp.responsavel.detail.title">Responsavel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="escolaEbdApp.responsavel.id">Id</Translate>
            </span>
          </dt>
          <dd>{responsavelEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="escolaEbdApp.responsavel.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{responsavelEntity.nome}</dd>
          <dt>
            <span id="cpf">
              <Translate contentKey="escolaEbdApp.responsavel.cpf">Cpf</Translate>
            </span>
          </dt>
          <dd>{responsavelEntity.cpf}</dd>
          <dt>
            <span id="sexo">
              <Translate contentKey="escolaEbdApp.responsavel.sexo">Sexo</Translate>
            </span>
          </dt>
          <dd>{responsavelEntity.sexo}</dd>
          <dt>
            <span id="parentesco">
              <Translate contentKey="escolaEbdApp.responsavel.parentesco">Parentesco</Translate>
            </span>
          </dt>
          <dd>{responsavelEntity.parentesco}</dd>
          <dt>
            <span id="dataCadastro">
              <Translate contentKey="escolaEbdApp.responsavel.dataCadastro">Data Cadastro</Translate>
            </span>
          </dt>
          <dd>
            {responsavelEntity.dataCadastro ? (
              <TextFormat value={responsavelEntity.dataCadastro} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="escolaEbdApp.responsavel.enderecos">Enderecos</Translate>
          </dt>
          <dd>
            {responsavelEntity.enderecos
              ? responsavelEntity.enderecos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {responsavelEntity.enderecos && i === responsavelEntity.enderecos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="escolaEbdApp.responsavel.alunos">Alunos</Translate>
          </dt>
          <dd>
            {responsavelEntity.alunos
              ? responsavelEntity.alunos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {responsavelEntity.alunos && i === responsavelEntity.alunos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/responsavel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/responsavel/${responsavelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResponsavelDetail;
