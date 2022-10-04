import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pessoa.reducer';

export const PessoaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pessoaEntity = useAppSelector(state => state.pessoa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pessoaDetailsHeading">
          <Translate contentKey="escolaEbdApp.pessoa.detail.title">Pessoa</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="escolaEbdApp.pessoa.id">Id</Translate>
            </span>
          </dt>
          <dd>{pessoaEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="escolaEbdApp.pessoa.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{pessoaEntity.nome}</dd>
          <dt>
            <span id="dataNascimento">
              <Translate contentKey="escolaEbdApp.pessoa.dataNascimento">Data Nascimento</Translate>
            </span>
          </dt>
          <dd>
            {pessoaEntity.dataNascimento ? (
              <TextFormat value={pessoaEntity.dataNascimento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cpf">
              <Translate contentKey="escolaEbdApp.pessoa.cpf">Cpf</Translate>
            </span>
          </dt>
          <dd>{pessoaEntity.cpf}</dd>
          <dt>
            <span id="sexo">
              <Translate contentKey="escolaEbdApp.pessoa.sexo">Sexo</Translate>
            </span>
          </dt>
          <dd>{pessoaEntity.sexo}</dd>
          <dt>
            <span id="tipoPessoa">
              <Translate contentKey="escolaEbdApp.pessoa.tipoPessoa">Tipo Pessoa</Translate>
            </span>
          </dt>
          <dd>{pessoaEntity.tipoPessoa}</dd>
          <dt>
            <span id="dataCadastro">
              <Translate contentKey="escolaEbdApp.pessoa.dataCadastro">Data Cadastro</Translate>
            </span>
          </dt>
          <dd>
            {pessoaEntity.dataCadastro ? <TextFormat value={pessoaEntity.dataCadastro} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dataAtualizacao">
              <Translate contentKey="escolaEbdApp.pessoa.dataAtualizacao">Data Atualizacao</Translate>
            </span>
          </dt>
          <dd>
            {pessoaEntity.dataAtualizacao ? <TextFormat value={pessoaEntity.dataAtualizacao} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="escolaEbdApp.pessoa.enderecos">Enderecos</Translate>
          </dt>
          <dd>
            {pessoaEntity.enderecos
              ? pessoaEntity.enderecos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {pessoaEntity.enderecos && i === pessoaEntity.enderecos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="escolaEbdApp.pessoa.turma">Turma</Translate>
          </dt>
          <dd>{pessoaEntity.turma ? pessoaEntity.turma.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pessoa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pessoa/${pessoaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PessoaDetail;
