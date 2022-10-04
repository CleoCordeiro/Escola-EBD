import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './turma.reducer';

export const TurmaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const turmaEntity = useAppSelector(state => state.turma.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="turmaDetailsHeading">
          <Translate contentKey="escolaEbdApp.turma.detail.title">Turma</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="escolaEbdApp.turma.id">Id</Translate>
            </span>
          </dt>
          <dd>{turmaEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="escolaEbdApp.turma.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{turmaEntity.nome}</dd>
          <dt>
            <span id="sexoTurma">
              <Translate contentKey="escolaEbdApp.turma.sexoTurma">Sexo Turma</Translate>
            </span>
          </dt>
          <dd>{turmaEntity.sexoTurma}</dd>
          <dt>
            <span id="faixaEtaria">
              <Translate contentKey="escolaEbdApp.turma.faixaEtaria">Faixa Etaria</Translate>
            </span>
          </dt>
          <dd>{turmaEntity.faixaEtaria}</dd>
          <dt>
            <span id="dataInicio">
              <Translate contentKey="escolaEbdApp.turma.dataInicio">Data Inicio</Translate>
            </span>
          </dt>
          <dd>
            {turmaEntity.dataInicio ? <TextFormat value={turmaEntity.dataInicio} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dataFim">
              <Translate contentKey="escolaEbdApp.turma.dataFim">Data Fim</Translate>
            </span>
          </dt>
          <dd>{turmaEntity.dataFim ? <TextFormat value={turmaEntity.dataFim} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="dataCadastro">
              <Translate contentKey="escolaEbdApp.turma.dataCadastro">Data Cadastro</Translate>
            </span>
          </dt>
          <dd>{turmaEntity.dataCadastro ? <TextFormat value={turmaEntity.dataCadastro} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="escolaEbdApp.turma.professor">Professor</Translate>
          </dt>
          <dd>{turmaEntity.professor ? turmaEntity.professor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/turma" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/turma/${turmaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TurmaDetail;
