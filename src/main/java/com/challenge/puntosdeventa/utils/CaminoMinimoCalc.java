package com.challenge.puntosdeventa.utils;

import com.challenge.puntosdeventa.entity.CostoPuntoVentaEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CaminoMinimoCalc {

    public Resultado calcular(
            Long origen,
            Long destino,
            List<CostoPuntoVentaEntity> costos
    ) {

        // 1) Armo grafo simple
        Map<Long, List<Edge>> graph = new HashMap<>();

        for (CostoPuntoVentaEntity  c : costos) {
            graph.computeIfAbsent(c.idA(), k -> new ArrayList<>())
                    .add(new Edge(c.idB(), c.costo()));
            graph.computeIfAbsent(c.idB(), k -> new ArrayList<>())
                    .add(new Edge(c.idA(), c.costo()));
        }

        // 2) Dijkstra
        Map<Long, Double> dist = new HashMap<>();
        Map<Long, Long> prev = new HashMap<>();
        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingDouble(n -> n.costo));

        dist.put(origen, 0.0);
        pq.add(new Node(origen, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.id.equals(destino)) break;

            for (Edge edge : graph.getOrDefault(current.id, List.of())) {
                double nuevoCosto = dist.get(current.id) + edge.costo;

                if (nuevoCosto < dist.getOrDefault(edge.destino, Double.MAX_VALUE)) {
                    dist.put(edge.destino, nuevoCosto);
                    prev.put(edge.destino, current.id);
                    pq.add(new Node(edge.destino, nuevoCosto));
                }
            }
        }

        if (!dist.containsKey(destino)) {
            throw new IllegalStateException("Destino inalcanzable");
        }

        // 3) Reconstruyo camino
        List<Long> camino = new ArrayList<>();
        for (Long at = destino; at != null; at = prev.get(at)) {
            camino.add(at);
        }
        Collections.reverse(camino);

        return new Resultado(dist.get(destino), camino);
    }

    //Clases internas

    private record Edge(Long destino, Double costo) {}
    private record Node(Long id, Double costo) {}

    public record Resultado(
            Double costoMinimo,
            List<Long> camino
    ) {}
}
