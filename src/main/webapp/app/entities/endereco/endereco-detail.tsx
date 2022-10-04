import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './endereco.reducer';

export const EnderecoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const enderecoEntity = useAppSelector(state => state.endereco.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enderecoDetailsHeading">
          <Translate contentKey="escolaEbdApp.endereco.detail.title">Endereco</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="escolaEbdApp.endereco.id">Id</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.id}</dd>
          <dt>
            <span id="logradouro">
              <Translate contentKey="escolaEbdApp.endereco.logradouro">Logradouro</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.logradouro}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="escolaEbdApp.endereco.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.numero}</dd>
          <dt>
            <span id="complemento">
              <Translate contentKey="escolaEbdApp.endereco.complemento">Complemento</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.complemento}</dd>
          <dt>
            <span id="bairro">
              <Translate contentKey="escolaEbdApp.endereco.bairro">Bairro</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.bairro}</dd>
          <dt>
            <span id="cidade">
              <Translate contentKey="escolaEbdApp.endereco.cidade">Cidade</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.cidade}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="escolaEbdApp.endereco.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.estado}</dd>
          <dt>
            <span id="cep">
              <Translate contentKey="escolaEbdApp.endereco.cep">Cep</Translate>
            </span>
          </dt>
          <dd>{enderecoEntity.cep}</dd>
        </dl>
        <Button tag={Link} to="/endereco" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/endereco/${enderecoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnderecoDetail;
